package com.example.employee.sys.controller;

import java.util.Date;

import com.example.employee.sys.entity.Error;
import com.example.employee.sys.exceptions.InternalServerException;
import com.example.employee.sys.exceptions.NotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*******
 * ExceptionController
 * 
 * This is a global exception handler. Any exception thrown in the code can be
 *   caught here. Two examples are given below.
 *   1. MethodArgumentNotValidException - this is handled by the helper
 *      class, ResponseEntityExceptionHandler. Overriding the method here to
 *      insert custom implementation to handle bad request scenarios.
 *   2. HttpRequestMethodNotSupportedException - this is handled by the helper
 *      class, ResponseEntityExceptionHandler. Overriding the method here to
 *      insert custom implementation to handle unsupported method scenarios.
 *   3. NotFoundException - this is a custom exception class.
 *   4. InternalServerException - this is the generic catch-all exception class.
 *      
 * BAD_REQUEST, CONFLICT, UNAUTHORIZED scenarios should be handled here too.
 * 
 * @RestControllerAdvice is used to intercept exceptions thrown in the app.
 * 
 * @ExceptionHandler is used on methods that will handle the specified exception
 *   class.
 * 
 * @author andie
 *
 */
@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

		
    /**
     * handleMethodArgumentNotValid()
     * This method already exists in the helper class, ResponseEntityExceptionHandler.
     *   Overriding it to inject our custom error object whenever a bad request 
     *   body is encountered. 
     * 
     * @return 400 status with the error object as response body
     */
    @Override
	public final ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException e,
	        HttpHeaders headers, 
	        HttpStatus status, 
	        WebRequest request) {
		Error error = new Error(new Date(), e.getClass().getSimpleName(), e.getMessage());
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
	
    /**
     * handleHttpRequestMethodNotSupported()
     * This method already exists in the helper class, ResponseEntityExceptionHandler.
     *   Overriding it to inject our custom error object whenever unsupported 
     *   methods are used on specific requests. 
     * 
     * @return 405 status with the error object as response body
     */
    @Override
	public final ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException e,
	        HttpHeaders headers, 
	        HttpStatus status, 
	        WebRequest request) {
		Error error = new Error(new Date(), e.getClass().getSimpleName(), e.getMessage());
		return new ResponseEntity<Object>(error, HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * handleNotFoundException()
	 * This catches and handles any resource not found scenarios. It creates an 
	 *   error object and populates it with important information that the API
	 *   consumer may want to know.
	 * 
	 * @param e
	 * @return 404 status with the error object as response body
	 */
	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
		Error error = new Error(new Date(), e.getClass().getSimpleName(), e.getMessage());
		return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * handleAllExceptions()
	 * This is the catch-all exception handler. Handles all types of exception
	 *   encountered in the code that were not caught by the other exception
	 *   handlers.
	 * 
	 * @param e
	 * @return 500 status with the error object as response body
	 */
	@ExceptionHandler(InternalServerException.class)
	public final ResponseEntity<Object> handleAllExceptions(InternalServerException e) {
		Error error = new Error(new Date(), e.getClass().getSimpleName(), e.getMessage());
	    return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
}