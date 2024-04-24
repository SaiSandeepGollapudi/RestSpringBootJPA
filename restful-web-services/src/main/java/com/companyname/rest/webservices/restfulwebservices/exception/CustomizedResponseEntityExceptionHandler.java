package com.companyname.rest.webservices.restfulwebservices.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.companyname.rest.webservices.restfulwebservices.user.UserNotFoundException;

@ControllerAdvice
//By default exception handling is done by Spring with ResponseEntityExceptionHandler so we inherit it to define custom exceptions
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)//@ExceptionHandler is how you define what exceptions you'd want to handle,
	//We want to handle all exceptions so Exception.class.
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(),// from predefined classes passed in the parameters of this method Exception ex
				request.getDescription(false));// WebRequest request
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserDefinedExceptions(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(),// from predefined classes passed in the parameters of this method Exception ex
				request.getDescription(false));// WebRequest request
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.NOT_FOUND);

	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				// from predefined classes passed in the parameters of this method Exception ex
		//concatenates the total number of errors (ex.getErrorCount()) with the message of the first error (ex.getFieldErrors().get(0)
		"Total Errors: " + ex.getErrorCount() + ", First Error: " + ex.getFieldErrors().getFirst(),
		//The request.getDescription(false) method retrieves a description of the request,
		//excluding any client-specific details. such as headers or cookies, in the description. 
		//Instead, it provides a general overview of the request. 		
		request.getDescription(false));// WebRequest request
	
		return new ResponseEntity<Object>(errorDetails,HttpStatus.BAD_REQUEST);
		
		
	}

	
}
