package com.example.employee.sys.entity;

import java.util.Date;

/***
 * Error model
 * 
 * This will hold the timestamp when the exception was encountered, the type of
 *   error and the details.
 * 
 * @author andie
 *
 */
public class Error {

	private Date timestamp;
	private String type;
	private String message;
	
	public Error(Date timestamp, String type, String message) {
		super();
		this.timestamp = timestamp;
		this.type = type;
		this.message = message;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
