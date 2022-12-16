package com.example.employee.sys.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.employee.sys.common.Utils;
import com.example.employee.sys.entity.Employee;
import com.example.employee.sys.exceptions.NotFoundException;
import com.example.employee.sys.service.EmployeeService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/***
 * EmployeeControllerTest
 * 
 * This is the test class for EmployeeController and demonstrates the use of the following 
 *   components and annotations:
 * 
 *   1. The @SpringBootTest annotation which loads the application context to be used for 
 *      the tests and manages it as the tests run. This means that we benefit from Spring's 
 *      dependency injection and autoconfiguration.
 *   2. The @AutoConfigureMockMvc annotation tells Spring that we want autoconfiguration of 
 *      the MockMvc enabled and to inject it when called as a dependency (@Autowired). This 
 *      is very handy and eliminates any configuration work. We can just focus on writing 
 *      tests.
 *   3. MockBean to define mock beans that are managed by the Spring application context. 
 *      The mock beans are injected by the application context by using @MockBean. For this
 *      one, the mock bean is the EmployeeService.
 *   4. The @Before and @After -related annotations are used to perform initial setups 
 *      before running tests and to do cleanups afterwards.
 *   5. MockMvc to trigger the call to the API endpoints and set the expectations where the 
 *      result of the requests will be compared to. 
 */
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Autowired
    private MockMvc mockMvc;
    
	@MockBean
	private EmployeeService service;
	
    private Employee mockEmployee;

    // To test BeforeEach, BeforeAll, AfterEach and AfterAll
    private static Logger logger = LoggerFactory.getLogger(EmployeeControllerTest.class);

    @BeforeAll
    static void setup() {
        logger.info("== Log me BEFORE running all test ==");
        // Add all processes here that need to run before the whole test 
    }

    @AfterAll
    static void teardown() {
        logger.info("== Log me AFTER running all test ==");
        // Add all processes here that need to run after the whole test
    }

    @BeforeEach
    void init() {
        logger.info("== Log me BEFORE each test method ==");
        this.mockEmployee = new Employee(1, "John", "Manager", Date.valueOf("2001-01-01"));
        // Add all processes here that need to run before each test method
    }

    @AfterEach
    void cleanup() {
        logger.info("== Log me AFTER each test method ==");
        // Add all processes here that need to run after each test method
    }

    /***
     * getEmployeesHasResult
     * 
     * This is a method to test the GET /employees endpoint with a result set. 
     * 
     * Here's the flow:
     * - Create a mock list of employees. This will be set as the return value of the Employee
     *   Service's getEmployees() method.
     * - Use Mockito's when-thenReturn to intercept any call to EmployeeService.getEmployees 
     *   method then return a mock object which is the mock list of employees.
     * - Call MockMvc.perform() to trigger the GET request.
     * - Use MockMvc.andExpect() to compare the actual result vs expected values.
     * 
     * @throws Exception
     */
	@Test
	@DisplayName("GET /employees WITH RESULT")
	void getEmployeesHasResult() throws Exception {
		// Create a mock employee list
		List<Employee> list = 
            Arrays.asList(
                new Employee(1, "John", "Manager", Date.valueOf("2001-01-01")),
                new Employee(2, "Jane", "Director", Date.valueOf("2002-02-02")),
                new Employee(3, "James", "Supervisor", Date.valueOf("2003-03-03"))
            );

        // Using the mock service, return the mock employee list when getEmployees() is called
        when(service.getEmployees()).thenReturn(list);
		
		// Execute the request
		mockMvc.perform(get("/employees"))
		
			// Validate the response code and content type
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
			// Validate the response body
			.andExpect(jsonPath("$.[0].employeeId", is(1)))
			.andExpect(jsonPath("$.[0].employeeName", is("John")))
			.andExpect(jsonPath("$.[0].position", is("Manager")))
			.andExpect(jsonPath("$.[0].dateHired", is("2001-01-01")));
	}
	
    /***
     * getEmployeesNoResult
     * 
     * This is a method to test the GET /employees endpoint with no data. 
     * 
     * Here's the flow:
     * - Use Mockito's when-thenReturn to intercept any call to EmployeeService.getEmployees 
     *   method then return an empty list.
     * - Call MockMvc.perform() to trigger the GET request.
     * - Use MockMvc.andExpect() to compare the actual result vs expected values.
     * 
     * @throws Exception
     */
	@Test
	@DisplayName("GET /employees NO RESULT")
	void getEmployeesNoResult() throws Exception {
		// Using the mock service, return an empty list when getEmployees() is called
		when(service.getEmployees()).thenReturn(new ArrayList<Employee>());
		
		// Execute the request
		mockMvc.perform(get("/employees"))
		
			// Validate the response
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))		
			.andExpect(content().string("[]"));
	}
	
    /***
     * getEmployeeByIdFound
     * 
     * This is a method to test the GET /employees/id endpoint with a matching resource. 
     * 
     * Here's the flow:
     * - No need to setup the test data here as it is handled by the init method with the 
     *   @BeforeEach annotation.
     * - Use Mockito's when-thenReturn to intercept any call to EmployeeService.getEmployeeById 
     *   method then return a mock object which is the mock employee.
     * - Call MockMvc.perform() to trigger the GET request.
     * - Use MockMvc.andExpect() to compare the actual result vs expected values.
     * 
     * @throws Exception
     */
    @Test
	@DisplayName("GET /employees/1 is FOUND")
	void getEmployeeByIdFound() throws Exception {
		// Using the mock service, return the mock employee when getEmployeeById() is called
		when(service.getEmployeeById(1)).thenReturn(Optional.of(this.mockEmployee));
		
		// Execute the request
		mockMvc.perform(get("/employees/{id}", 1))
		
			// Validate the response code and content type
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
			// Validate the response body
			.andExpect(jsonPath("$.employeeId", is(1)))
			.andExpect(jsonPath("$.employeeName", is("John")))
			.andExpect(jsonPath("$.position", is("Manager")))
			.andExpect(jsonPath("$.dateHired", is("2001-01-01")));
	}

    /***
     * getEmployeeByIdNotFound
     * 
     * This is a method to test the GET /employees/id endpoint with a matching resource. 
     * 
     * Here's the flow:
     * - Use Mockito's when-thenThrow to intercept any call to EmployeeService.getEmployeeById 
     *   method then return an exception to simulate a resource not found scenario.
     * - Call MockMvc.perform() to trigger the GET request.
     * - Use MockMvc.andExpect() to compare the actual result vs expected values.
     * 
     * @throws Exception
     */    
	@Test
	@DisplayName("GET /employees/999 is NOT FOUND")
	void getEmployeeByIdNotFound() throws Exception {
		// Using the mock service, return a NotFoundException when resource is not found 
        Integer nonExistentId = 999;
		when(service.getEmployeeById(nonExistentId)).thenThrow(new NotFoundException(nonExistentId));
		
		// Execute the request
		mockMvc.perform(get("/employees/{id}", nonExistentId))
		
			// Validate the response code and content type
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
			// Validate the response body
			.andExpect(jsonPath("$.type", is("NotFoundException")))
            .andExpect(jsonPath("$.message", is("Unable to find employee with ID 999")));
	}    
	
    /***
     * addEmployeeSuccess
     * 
     * This is a method to test the POST /employees. 
     * 
     * Here's the flow:
     * - No need to setup the test data here as it is handled by the init method with the 
     *   @BeforeEach annotation.
     * - Use Mockito's when-thenReturn to intercept any call to EmployeeService.saveEmployee 
     *   method then return 1 as the mock employee ID.
     * - Call MockMvc.perform() to trigger the POST request.
     * - Use MockMvc.andExpect() to compare the actual result vs expected values.
     * 
     * @throws Exception
     */    
	@Test
	@DisplayName("POST /employees is SUCCESSFUL")
	void addEmployeeSuccess() throws Exception {
		// Using the mock service, return a mock ID when saveEmployee() is called 
		when(service.saveEmployee(any())).thenReturn(1);
		
		// Execute the request
		mockMvc.perform(post("/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Utils.getJsonString(this.mockEmployee)))
		
			// Validate the response status code and Location header
			.andExpect(status().isCreated())
			.andExpect(header().string(HttpHeaders.LOCATION, "/employees/1"));
	}

    /***
     * deleteEmployeeByIdFound
     * 
     * This is a method to test the DELETE /employees/id. 
     * 
     * Here's the flow:
     * - Use Mockito's doNothing-when to intercept any call to EmployeeService.deleteEmployee 
     *   method then do nothing since the method returns void.
     * - Call MockMvc.perform() to trigger the DELETE request.
     * - Use MockMvc.andExpect() to compare the actual result vs expected values.
     * 
     * @throws Exception
     */     
	@Test
	@DisplayName("DELETE /employees/1 is FOUND")
	void deleteEmployeeByIdFound() throws Exception {
		// Using the mock service, return the mock employee when getEmployeeById() is called
        doNothing().when(service).deleteEmployee(1);
		
		// Execute the request
		mockMvc.perform(delete("/employees/{id}", 1))
		
			// Validate the response code and content type
			.andExpect(status().isNoContent());
	}
    
    /***
     * deleteEmployeeByIdNotFound
     * 
     * This is a method to test the DELETE /employees/id and the resource is not found. 
     * 
     * Here's the flow:
     * - Use Mockito's doThrow-when to intercept any call to EmployeeService.deleteEmployee 
     *   method then throw a resource not found exception.
     * - Call MockMvc.perform() to trigger the DELETE request.
     * - Use MockMvc.andExpect() to compare the actual result vs expected values.
     * 
     * @throws Exception
     */     
	@Test
	@DisplayName("DELETE /employees/999 is NOT FOUND")
	void deleteEmployeeByIdNotFound() throws Exception {
		// Using the mock service, return a NotFoundException when resource is not found 
        Integer nonExistentId = 999;
        doThrow(new NotFoundException(nonExistentId)).when(service).deleteEmployee(nonExistentId);
		
		// Execute the request
		mockMvc.perform(delete("/employees/{id}", nonExistentId))
		
			// Validate the response code and content type
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
			// Validate the response body
			.andExpect(jsonPath("$.type", is("NotFoundException")))
            .andExpect(jsonPath("$.message", is("Unable to find employee with ID 999")));
	}    
    
}
