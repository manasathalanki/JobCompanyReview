package com.example.youtube_learning.exceptions;

import java.util.Date;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = CompanyIdNotFOund.class)
	public ResponseEntity<ApiError> companyIdnotFoundException() {
		
		ApiError apiError = new ApiError(400, "Id not found", new Date());
		
		return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = JobIdNotFound.class)
	public ResponseEntity<ApiError> jobIdnotFoundException() {
		
		ApiError apiError = new ApiError(400, "Id not found", new Date());
		
		return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
	}
	
	
}
