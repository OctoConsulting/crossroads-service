package com.gov.travelservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

	public static final String NO_DATA_FOUND = "No data found";
	public static final String DEFAULT_FAILURE = "Error occurred while processing request";
	public static final String DEFAULT_SUCCESS = "Request successfully processed";
	public static final String EMPTY_DATA = "{}";

	private ResponseBuilder() {

	}

	public static ResponseEntity<Object> build(HttpStatus httpResponseCode, Boolean success, String message) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", success);
		responseBody.put("message", message);
		return ResponseEntity.status(httpResponseCode).body(responseBody);
	}

	public static ResponseEntity<Object> build(HttpStatus httpResponseCode, Object responseData) {
		return ResponseEntity.status(httpResponseCode).body(responseData);
	}

}
