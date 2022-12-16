package com.example.employee.sys.exceptions;

/**
 * This is a sample custom exception. More information can be returned to the
 *   API consumer about the error instead of just a stack trace. Customized
 *   messages can be returned.
 *   
 * @author andie
 *
 */
public class NotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = -7983968386629429924L;

	public NotFoundException(Integer id) {
        super(String.format("Unable to find employee with ID %d", id));
    }
}
