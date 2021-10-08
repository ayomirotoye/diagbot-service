package com.seamhealthgroup.diagbot.exceptions;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.ServiceUnavailableException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamhealthgroup.diagbot.enums.ResponseCodeEnum;

import lombok.extern.slf4j.Slf4j;

@Order(1000)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
	@Autowired
	ObjectMapper objectMapper;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public Errors validationExceptionHandler(MethodArgumentNotValidException ex) {
		Map<String, String> responseBody = new HashMap<>();

		Errors errors = new Errors(ResponseCodeEnum.BAD_REQUEST.getResponseCode(),
				ResponseCodeEnum.BAD_REQUEST.getResponseMessage());
		try {
			ex.getBindingResult().getAllErrors().forEach(error -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				responseBody.put(fieldName, errorMessage);
			});
			errors.setData(responseBody);
		} catch (Exception e) {
			if (ex.getBindingResult() != null) {
				errors = processFieldErrors(ex.getBindingResult().getAllErrors(), errors, responseBody);
			}
		}
		return errors;
	}

	private Errors processFieldErrors(List<ObjectError> fieldErrors, Errors errors, Map<String, String> responseBody) {

		if (fieldErrors != null) {
			fieldErrors.stream().forEach(error -> {
				String fieldName = error.getCode();
				String errorMessage = error.getDefaultMessage();
				responseBody.put(fieldName, errorMessage);
			});
			errors.setData(
					responseBody != null ? responseBody.values() : ResponseCodeEnum.BAD_REQUEST.getResponseMessage());
		}

		return errors;
	}

	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ SocketTimeoutException.class })
	public ResponseEntity<Map<String, Object>> validationExceptionHandler(SocketTimeoutException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.SOCKET_TIMEOUT.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.SOCKET_TIMEOUT.getResponseMessage());
		errors.put("data", ResponseCodeEnum.SOCKET_TIMEOUT.getResponseMessage());
		log.debug("==================== ERROR OCCURRED =================" + ex);
		return new ResponseEntity<>(errors, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Map<String, Object>> RunTimeException(RuntimeException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseMessage());
		errors.put("data", ex.getClass());
		log.debug("==================== ERROR OCCURRED =================" + ex);
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ HttpMessageConversionException.class })
	public ResponseEntity<Map<String, Object>> handleConversionEception(HttpMessageConversionException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseMessage());
		errors.put("data", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseMessage());
		log.debug("==================== ERROR OCCURRED =================" + ex);
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ UnexpectedTypeException.class })
	public ResponseEntity<Map<String, Object>> handleUnexpectedTypeException(UnexpectedTypeException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.BAD_REQUEST.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.BAD_REQUEST.getResponseMessage());
		errors.put("data", ResponseCodeEnum.BAD_REQUEST.getResponseMessage());
		log.debug("==================== ERROR OCCURRED =================" + ex);
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ConstraintViolationException.class })
	public Errors handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, String> responseBody = new HashMap<>();

		Errors errors = new Errors(ResponseCodeEnum.BAD_REQUEST.getResponseCode(),
				ResponseCodeEnum.BAD_REQUEST.getResponseMessage());
		try {
			for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
				responseBody.put(violation.getPropertyPath().toString(), violation.getMessage());
			}

			errors.setData(responseBody);
		} catch (Exception e) {

		}
		return errors;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ NullPointerException.class })
	public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseMessage());
		errors.put("data", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseMessage());
		log.error("==================== ERROR OCCURRED =================" + ex);
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ InvalidDataAccessResourceUsageException.class })
	public ResponseEntity<Map<String, Object>> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseMessage());
		errors.put("data", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseMessage());
		log.error("==================== ERROR OCCURRED =================" + ex);
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public Map<String, Object> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.METHOD_NOT_ALOWED.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.METHOD_NOT_ALOWED.getResponseMessage());
		errors.put("data", ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ InternalServerErrorException.class })
	public Map<String, Object> handleInternalServerErrorException(InternalServerErrorException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.ERROR_OCCURRED.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.ERROR_OCCURRED.getResponseMessage());
		errors.put("data", ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	public Map<String, Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.UNSUPPORTED_MEDIA_TYPE.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.UNSUPPORTED_MEDIA_TYPE.getResponseMessage());
		errors.put("data", ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ HttpClientErrorException.class })
	public Map<String, Object> handleHttpClientErrorException(HttpClientErrorException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.BAD_REQUEST.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.BAD_REQUEST.getResponseMessage());
		errors.put("data", ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ ServiceUnavailableException.class })
	public Map<String, Object> handleServiceUnavailableException(ServiceUnavailableException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseMessage());
		errors.put("data", ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ ResourceAccessException.class })
	public Map<String, Object> handleResourceAccessException(ResourceAccessException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseMessage());
		errors.put("data", ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public Map<String, Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.BAD_REQUEST.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.BAD_REQUEST.getResponseMessage());
		errors.put("data", ex.getHttpInputMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ HttpServerErrorException.class })
	public Map<String, Object> handleHttpServerErrorException(HttpServerErrorException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("responseCode", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseCode());
		errors.put("responseMessage", ResponseCodeEnum.INTERNAL_SYSTEM_ERROR_DO_NOT_REQUERY.getResponseMessage());
		errors.put("data", ex.getMessage());
		return errors;
	}

}
