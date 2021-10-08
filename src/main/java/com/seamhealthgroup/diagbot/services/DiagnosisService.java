package com.seamhealthgroup.diagbot.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamhealthgroup.diagbot.configs.ApplicationProperties;
import com.seamhealthgroup.diagbot.configs.RestConnector;
import com.seamhealthgroup.diagbot.exceptions.AuthenticationException;
import com.seamhealthgroup.diagbot.models.GetDiagnosis;
import com.seamhealthgroup.diagbot.models.Issue;
import com.seamhealthgroup.diagbot.models.IssueDesc;
import com.seamhealthgroup.diagbot.models.Symptoms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnosisService {
	final AuthService authService;

	final ApplicationProperties applicationProperties;
	final RestConnector restConnector;
	final ObjectMapper objectMapper;

	private HttpHeaders headers = new HttpHeaders();

	public List<Symptoms> getSymptoms() throws AuthenticationException {
		List<Symptoms> symptomsList = new ArrayList<>();
		try {

			String accessToken = authService.getToken();
			ResponseEntity<String> responseStr = restConnector.exchange("",
					applicationProperties.getHealthService()
							.concat("/" + applicationProperties.getSymptomsEndpoint() + "?token=" + accessToken
									+ "&language=" + applicationProperties.getSymptomLanguage()),
					headers, HttpMethod.GET);
			if (responseStr.getStatusCode().is2xxSuccessful() && responseStr.hasBody()) {

				symptomsList = objectMapper.readValue(responseStr.getBody(), new TypeReference<List<Symptoms>>() {
				});
			}

		} catch (Exception e) {
			log.error("==============ERROR OCCURRED =================");
			throw new AuthenticationException(e);
		}
		return symptomsList;
	}

	public IssueDesc getIssueDesc(String issueId) throws AuthenticationException {
		IssueDesc symptomsList = null;
		try {

			String accessToken = authService.getToken();
			ResponseEntity<String> responseStr = restConnector.exchange("",
					applicationProperties.getHealthService()
							.concat("/" + applicationProperties.getIssuesEndpoint() + "/" + issueId + "/info?token="
									+ accessToken + "&language=" + applicationProperties.getSymptomLanguage()),
					headers, HttpMethod.GET);
			if (responseStr.getStatusCode().is2xxSuccessful() && responseStr.hasBody()) {

				symptomsList = objectMapper.readValue(responseStr.getBody(), IssueDesc.class);
			}

		} catch (Exception e) {
			log.error("==============ERROR OCCURRED =================");
			throw new AuthenticationException(e);
		}
		return symptomsList;
	}

	public List<Issue> getDiagnosis(GetDiagnosis diagnosis) throws AuthenticationException {
		List<Issue> diagnosisList = new ArrayList<>();
		try {

			String accessToken = authService.getToken();
			ResponseEntity<String> responseStr = restConnector.exchange("", applicationProperties.getHealthService()
					.concat("/" + applicationProperties.getSymptomsEndpoint() + "?token=" + accessToken + "&language="
							+ applicationProperties.getSymptomLanguage() + "&symptoms=" + diagnosis.getSymptoms()
							+ "&gender=" + diagnosis.getGender() + "&year_of_birth=" + diagnosis.getYearOfBirth()),
					headers, HttpMethod.GET);
			if (responseStr.getStatusCode().is2xxSuccessful() && responseStr.hasBody()) {

				diagnosisList = objectMapper.readValue(responseStr.getBody(), new TypeReference<List<Issue>>() {
				});
			}

		} catch (Exception e) {
			log.error("==============ERROR OCCURRED =================");
			throw new AuthenticationException(e);
		}
		return diagnosisList;
	}
}
