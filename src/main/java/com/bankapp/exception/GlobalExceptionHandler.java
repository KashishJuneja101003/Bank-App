package com.bankapp.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException e){

	    Map<String, String> fieldErrors = e.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .collect(Collectors.toMap(
	                    error -> error.getField(),
	                    error -> error.getDefaultMessage()
	            ));

	    ApiError apiError = ApiError.builder()
	            .status(HttpStatus.BAD_REQUEST.value())
	            .error(HttpStatus.BAD_REQUEST.name())
	            .message("Validation failed for request")
	            .localDateTime(LocalDateTime.now())
	            .fieldErrors(fieldErrors)
	            .build();

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(apiError);
	}
	
	@ExceptionHandler(UpdateRequestBodyValidationException.class)
	public ResponseEntity<ApiError> handleUpdateValidationException(UpdateRequestBodyValidationException e){

	    ApiError apiError = ApiError.builder()
	            .status(HttpStatus.BAD_REQUEST.value())
	            .error(HttpStatus.BAD_REQUEST.name())
	            .message(e.getMessage())
	            .localDateTime(LocalDateTime.now())
	            .build();

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(apiError);
	}

	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ApiError> handleInsufficientBalanceException(InsufficientBalanceException e){

	    ApiError apiError = ApiError.builder()
	            .status(HttpStatus.BAD_REQUEST.value())
	            .error(HttpStatus.BAD_REQUEST.name())
	            .message(e.getMessage())
	            .localDateTime(LocalDateTime.now())
	            .build();

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(apiError);
	}
	
	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<ApiError> handleTransactionNotFoundException(TransactionNotFoundException e){

	    ApiError apiError = ApiError.builder()
	            .status(HttpStatus.BAD_REQUEST.value())
	            .error(HttpStatus.BAD_REQUEST.name())
	            .message(e.getMessage())
	            .localDateTime(LocalDateTime.now())
	            .build();

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(apiError);
	}

}
