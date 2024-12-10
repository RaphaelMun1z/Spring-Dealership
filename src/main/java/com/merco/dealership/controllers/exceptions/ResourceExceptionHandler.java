package com.merco.dealership.controllers.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.RequiredObjectIsNullException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ResourceExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Resource not found");
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Database error");
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DataViolationException.class)
	public ResponseEntity<StandardError> dataViolation(DataViolationException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Data violation error");
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolation(DataIntegrityViolationException e,
			HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Data violation error");
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, "Couldn't register the entity.",
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		e.getBindingResult().getAllErrors().forEach((err) -> {
			String fieldName = ((FieldError) err).getField();
			String errorMessage = err.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		StandardError err = new StandardError(Instant.now(), status.value(), errors,
				"Please, ensure you use one of the values accepted by the server.", request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandardError> httpMessageNotReadable(HttpMessageNotReadableException e,
			HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		Throwable rootCause = e.getMostSpecificCause();

		String errorMessage = "Invalid value for field. Please ensure the value matches one of the accepted enum values.";

		if (rootCause instanceof InvalidFormatException) {
			InvalidFormatException invalidFormatException = (InvalidFormatException) rootCause;
			String fieldName = invalidFormatException.getPath().get(0).getFieldName();
			String invalidValue = invalidFormatException.getValue().toString();
			errorMessage = String.format("Invalid value '%s'. Please use a valid value.", invalidValue, fieldName);
			errors.put(fieldName, errorMessage);
		}

		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), errors,
				"The provided value is not valid for the expected field. Please ensure you use one of the values accepted by the server.",
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(RequiredObjectIsNullException.class)
	public ResponseEntity<StandardError> requiredObjectIsNull(RequiredObjectIsNullException e,
			HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Required object is null.");
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, "Required object is null.",
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<StandardError> badCredentials(BadCredentialsException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Bad credentials.");
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, "Bad credentials.",
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}