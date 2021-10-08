package com.seamhealthgroup.diagbot.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 *
 * @author VFDNBK-330
 */

@Configuration
@Data
public class ApplicationProperties {
	@Value("${apimedic.api_username}")
	private String apiKey;
	@Value("${apimedic.api_password}")
	private String secretKey;
	@Value("${apimedic.auth_service}")
	private String authService;
	@Value("${apimedic.health_service}")
	private String healthService;
	@Value("${apimedic.endpoints.symptoms}")
	private String symptomsEndpoint;
	@Value("${apimedic.endpoints.diagnosis}")
	private String diagnosisEndpoint;
	@Value("${apimedic.endpoints.issues}")
	private String issuesEndpoint;
	@Value("${apimedic.language}")
	private String symptomLanguage;
}
