package com.seamhealthgroup.diagbot.services;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamhealthgroup.diagbot.configs.ApplicationProperties;
import com.seamhealthgroup.diagbot.configs.RestConnector;
import com.seamhealthgroup.diagbot.exceptions.AuthenticationException;
import com.seamhealthgroup.diagbot.models.AuthModel;
import com.seamhealthgroup.diagbot.utilities.GeneralUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

	private ApplicationProperties applicationProperties;
	private RestConnector restConnector;
	private GeneralUtils generalUtils;
	private ObjectMapper objectMapper;

	private HttpHeaders headers = new HttpHeaders();

	@Autowired
	public AuthService(ApplicationProperties applicationProperties, RestConnector restConnector,
			GeneralUtils generalUtils, ObjectMapper objectMapper) {
		this.applicationProperties = applicationProperties;
		this.restConnector = restConnector;
		this.generalUtils = generalUtils;
		this.objectMapper = objectMapper;
	}

	@PostConstruct
	public void setHeaders() throws IOException, AuthenticationException {
		headers.add("Authorization",
				"Bearer ".concat(applicationProperties.getApiKey() + ":" + generalUtils.hmac256()));
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	public String getToken() throws AuthenticationException {
		String accessToken = "";
		try {

			ResponseEntity<String> responseStr = restConnector.exchange("", applicationProperties.getAuthService(),
					headers, HttpMethod.POST);
			if (responseStr.getStatusCode().is2xxSuccessful() && responseStr.hasBody()) {
				AuthModel authModel = objectMapper.readValue(responseStr.getBody(), AuthModel.class);
				accessToken = authModel.getToken();
				
				log.info("Token:::"+accessToken);
			}

		} catch (Exception e) {
			log.error("==============ERROR OCCURRED =================");
			throw new AuthenticationException(e);
		}
		return accessToken;
	}
}
