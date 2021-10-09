package com.seamhealthgroup.diagbot.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seamhealthgroup.diagbot.enums.ResponseCodeEnum;
import com.seamhealthgroup.diagbot.exceptions.AuthenticationException;
import com.seamhealthgroup.diagbot.exceptions.InternalServerErrorException;
import com.seamhealthgroup.diagbot.models.GenericResponse;
import com.seamhealthgroup.diagbot.models.GetDiagnosis;
import com.seamhealthgroup.diagbot.models.Issue;
import com.seamhealthgroup.diagbot.models.IssueDesc;
import com.seamhealthgroup.diagbot.models.Symptoms;
import com.seamhealthgroup.diagbot.services.DiagnosisService;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3001")
public class DiagnosisController {

	private DiagnosisService diagnosisService;

	@Autowired
	public DiagnosisController(DiagnosisService diagnosisService) {
		this.diagnosisService = diagnosisService;
	}

	@PostMapping("/diagnosis")
	public ResponseEntity<?> doDiagnosis(@RequestBody @Valid GetDiagnosis getDiagnosis)
			throws InternalServerErrorException, AuthenticationException {
		List<Issue> getResponse = diagnosisService.getDiagnosis(getDiagnosis);

		return !getResponse.isEmpty() ? new ResponseEntity<>(getResponse, HttpStatus.OK)
				: new ResponseEntity<>(
						GenericResponse.builder().responseCode(ResponseCodeEnum.NO_RECORD_FOUND.getResponseCode())
								.responseMessage(ResponseCodeEnum.NO_RECORD_FOUND.getResponseMessage()).build(),
						HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/issue/{issueId}")
	public ResponseEntity<?> getIssueDesc(@PathVariable String issueId)
			throws InternalServerErrorException, AuthenticationException {
		IssueDesc getResponse = diagnosisService.getIssueDesc(issueId);

		return getResponse != null ? new ResponseEntity<>(getResponse, HttpStatus.OK)
				: new ResponseEntity<>(
						GenericResponse.builder().responseCode(ResponseCodeEnum.NO_RECORD_FOUND.getResponseCode())
								.responseMessage(ResponseCodeEnum.NO_RECORD_FOUND.getResponseMessage()).build(),
						HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/symptoms")
	public ResponseEntity<?> getSymptoms() throws InternalServerErrorException, AuthenticationException {
		List<Symptoms> getResponse = diagnosisService.getSymptoms();

		return !getResponse.isEmpty() ? new ResponseEntity<>(getResponse, HttpStatus.OK)
				: new ResponseEntity<>(
						GenericResponse.builder().responseCode(ResponseCodeEnum.NO_RECORD_FOUND.getResponseCode())
								.responseMessage(ResponseCodeEnum.NO_RECORD_FOUND.getResponseMessage()).build(),
						HttpStatus.BAD_REQUEST);
	}

}