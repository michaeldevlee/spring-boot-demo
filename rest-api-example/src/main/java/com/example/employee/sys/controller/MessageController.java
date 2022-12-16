package com.example.employee.sys.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/****
 * MessageController
 * 
 * The purpose of this controller class is to demonstrate a basic Hello World 
 *   API with a couple of samples. 
 *   - @Value annotation example
 *   - @GetMapping vs @RequestMapping
 *   - Response body as application/json vs plain/text
 * 
 * @author andie
 *
 */
@RestController
// @Profile("local")
public class MessageController {
	
	/*
	 * Use @Value to get the message property from the configuration properties
	 *   (application.properties). If the message property is not found, use the
	 *   "hello default" value.
	 *   
	 * This can also be used to take a VM argument or environment variable. If
	 *   the message property is present in multiple locations, Spring follows
	 *   this hierarchy: ENV VAR > VM ARG > local properties file
	 */
	@Value("${message:hello default}")
	private String message;
	
	/*
	 * This demonstrates handling a GET request using the GetMapping.
	 * 
	 * Returns a string containing the message and by default sets the status code to 200 OK. 
	 */
	@GetMapping("/message/text")
	public String getTextMessage() {
		return message;
	}
	
	/*
	 * This is similar to getTextMessage() but uses RequestMapping and sets the
	 *   response as a JSON-formatted string. 	
	 */
	@RequestMapping(
			path = "/message/json", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE
			)
    public String getMessageUsingResponseMapping() {
        return message;
    }

}
