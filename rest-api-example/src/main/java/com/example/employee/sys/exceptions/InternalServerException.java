package com.example.employee.sys.exceptions;

/**
 * This is a sample custom exception. More information can be returned to the
 *   API consumer about the error instead of just a stack trace. Customized
 *   messages can be returned.
 *   
 * @author andie
 *
 */
public class InternalServerException extends RuntimeException {
    
	private static final long serialVersionUID = -7983968386629429924L;

	public InternalServerException(String msg) {
        super(String.format("The service has encountered an issue. Here are the details: %s" ,msg));
    }
}
