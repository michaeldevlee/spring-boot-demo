package com.example.employee.sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 * This is the main application.	
 * 
 * The @SpringBootApplication annotation is used to enable auto configuration, 
 * 	scanning of components for beans, and loading of configuration from the  
 * 	application.properties/yaml file.
 * 
 *  @author andie
 */
@SpringBootApplication
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

}
