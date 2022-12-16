package com.example.employee.sys.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import com.example.employee.sys.entity.Employee;
import com.example.employee.sys.exceptions.InternalServerException;
import com.example.employee.sys.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/****
 * EmployeeController
 * 
 * Intercepts client requests and maps them to the appropriate handler. This
 *   controller contains the 5 common API operations. This follows the API 
 *   operations defined in the OAS training.
 *   
 * The implementation for each API operation below are not complete or optimized.
 *   This is intentional so trainees will write their own implementation. These
 *   are just samples.
 * 
 * @RestController is used to annotate this class as a Controller component that
 *   returns a response body.
 * 
 * @GetMapping, @PostMapping, @PatchMapping and @DeleteMapping are used to map
 *   requests to specific handlers.
 *   
 * @Autowired is used here to tell the application context that we need an
 *   instance of the EmployeeService bean.
 *   
 * @PathVariable is used to map a URL path parameter to a method variable.
 * 
 * @RequestBody is used to map an HTTP request body to a method variable.
 * 
 * @Valid is a an annotation that marks the associated parameter for validation. 
 * 
 * @author andie
 *
 */
@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeService service;
	
	@GetMapping("/employees")
	public ResponseEntity<Object> getEmployees() {
		return ResponseEntity.ok().body(service.getEmployees()); 
	}
	
	@GetMapping("/employees/{employeeId}")
	public ResponseEntity<Object> getEmployeeById(
			@PathVariable("employeeId") Integer employeeId) {
		return ResponseEntity.ok().body(service.getEmployeeById(employeeId)); 
	}
	
	@PostMapping("/employees")
	public ResponseEntity<Object> saveEmployee(
			@Valid @RequestBody Employee employee) {
		Integer employeeId = service.saveEmployee(employee);
		try {
			return ResponseEntity
					.created(new URI(String.format("/employees/%s", employeeId)))
                    .build();
		} catch (URISyntaxException e) {
			throw new InternalServerException("The URI in the Location header in POST /employees has an error.");
		} 
	}
	
	@PatchMapping("/employees/{employeeId}")
	public ResponseEntity<Object> updateEmployee(
			@RequestBody Employee employee,
			@PathVariable("employeeId") Integer employeeId) {
		service.updateEmployee(employee, employeeId);
		return ResponseEntity.noContent().build();
	}	
	
	@DeleteMapping("/employees/{employeeId}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable("employeeId") Integer employeeId) {
		service.deleteEmployee(employeeId);
		return ResponseEntity.noContent().build();
	}
}
