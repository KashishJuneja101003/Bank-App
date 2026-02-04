package com.bankapp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ApiError> handleAccountNotFound(AccountNotFoundException e){
		ApiError apiError = ApiError.builder()
				.localDateTime(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.message(e.getMessage())
				.error(HttpStatus.NOT_FOUND.name())
				.build();
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(apiError);
	}
	
	@ExceptionHandler(AccountAlreadyExistsException.class)
	public ResponseEntity<ApiError> handleAccountAlreadyExists(AccountAlreadyExistsException e){
		
		ApiError apiError = ApiError.builder()
				.status(HttpStatus.CONFLICT.value())
				.message(e.getMessage())
				.error(HttpStatus.CONFLICT.name())
				.build();
		
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(apiError);
	}
}
