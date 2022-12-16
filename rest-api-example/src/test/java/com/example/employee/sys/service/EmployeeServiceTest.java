package com.example.employee.sys.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.employee.sys.entity.Employee;
import com.example.employee.sys.exceptions.NotFoundException;
import com.example.employee.sys.repository.EmployeeRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/***
 * EmployeeServiceTest
 * 
 * This is the test class for EmployeeService and demonstrates the use of the following 
 *   components and annotations:
 * 
 *   1. The @SpringBootTest annotation which loads the application context to be used for 
 *      the tests and manages it as the tests run. This means that we benefit from Spring's 
 *      dependency injection and autoconfiguration.
 *   2. MockBean to define mock beans that are managed by the Spring application context. 
 *      The mock beans are injected by the application context by using @MockBean. For this
 *      one, the mock bean is the EmployeeRepository.
 *   3. The @Before and @After -related annotations are used to perform initial setups 
 *      before running tests and to do cleanups afterwards.
 */
@SpringBootTest
public class EmployeeServiceTest {

	@Autowired
	EmployeeService service;
	
	@MockBean
	EmployeeRepository repo;

    private Employee mockEmployee;
	
    @BeforeEach
    void init() {
        this.mockEmployee = new Employee(1, "John", "Manager", Date.valueOf("2001-01-01"));
    }

    /***
     * getEmployeesHasResult
     * 
     * This is a method to test the EmployeeService.getEmployees method with a result set. 
     * 
     * Here's the flow:
     * - Create a mock list of employees. This will be set as the return value of the Employee
     *   Service's getEmployees() method.
     * - Use Mockito's when-thenReturn to intercept the call to the JPA repository's findAll 
     *   method then return a mock object which is the mock list of employees.
     * - Call the EmployeeService.getEmployees method.
     * - Use Assertions to compare the actual result vs expected values.
     * 
     * @throws Exception
     */    
	@Test
	@DisplayName("TEST getEmployeesHasResult")
	void getEmployeesHasResult() throws Exception {
		// Create a mock employee list
		List<Employee> list = 
            Arrays.asList(
                new Employee(1, "John", "Manager", Date.valueOf("2001-01-01")),
                new Employee(2, "Jane", "Director", Date.valueOf("2002-02-02")),
                new Employee(3, "James", "Supervisor", Date.valueOf("2003-03-03"))
            );
        
        // Using the mock repo, return a list of mock employees when findAll() is called
		when(repo.findAll()).thenReturn(list);
		
		// Call the service
		List<Employee> returnedList = (List<Employee>) service.getEmployees();
		
		// Validate the result
		Assertions.assertFalse(returnedList.isEmpty(), "No result.");
		Assertions.assertEquals("James", returnedList.get(2).getEmployeeName());
        // do some more assertions
	}

    /***
     * getEmployeeByIdFound
     * 
     * This is a method to test the EmployeeService.getEmployeeById method with a matching
     *   resource. 
     * 
     * Here's the flow:
     * - No need to setup the test data here as it is handled by the init method with the 
     *   @BeforeEach annotation.
     * - Use Mockito's when-thenReturn to intercept the call to the following JPA repository
     *   methods:
     *     - when existsById then return true.
     *     - when findById then return a mock object which is the mock employee.
     * - Call the EmployeeService.getEmployeeById method.
     * - Use Assertions to compare the actual result vs expected values.
     * 
     * @throws Exception
     */     
	@Test
	@DisplayName("TEST getEmployeeByIdFound")
	void getEmployeeByIdFound() throws Exception {
		// Use the mock repo to get mock a return value from existsById() and findById()
        when(repo.existsById(1)).thenReturn(true);
		when(repo.findById(1)).thenReturn(Optional.of(this.mockEmployee));
		
		// Call the service
		Optional<Employee> returnedEmployee = service.getEmployeeById(1);
		
		// Validate the result
		Assertions.assertEquals(this.mockEmployee, returnedEmployee.get());
        // do some more assertions
	}

    /***
     * getEmployeeByIdNotFound
     * 
     * This is a method to test the EmployeeService.getEmployeeById method without a 
     *   matching resource. 
     * 
     * Here's the flow:
     * - Use Mockito's when-thenReturn to intercept the call to the JPA repository
     *    existsById method then return false.
     * - Call the EmployeeService.getEmployeeById method.
     * - Use Assertions to compare the actual result vs expected values.
     * 
     * @throws Exception
     */     
	@Test
	@DisplayName("TEST getEmployeeByIdNotFound")
	void getEmployeeByIdNotFound() throws Exception {
		// Use the mock repo to get mock a return value from existsById()
        when(repo.existsById(999)).thenReturn(false);

        // Validate that we get a NotFoundException when resource does not exist
		Assertions.assertThrows(NotFoundException.class, () -> service.getEmployeeById(999));
	}

    /***
     * saveEmployee
     * 
     * This is a method to test the EmployeeService.saveEmployee method. 
     * 
     * Here's the flow:
     * - No need to setup the test data here as it is handled by the init method with the 
     *   @BeforeEach annotation.
     * - Use Mockito's when-thenReturn to intercept the call to the JPA repository save
     *   methods then return a mock object which is the mock employee.
     * - Call the EmployeeService.saveEmployee method passing a mock request body.
     * - Use Assertions to compare the actual result vs expected values.
     * 
     * @throws Exception
     */      
	@Test
	@DisplayName("TEST saveEmployee")
	void saveEmployee() throws Exception {
		// Using the mock repo, return a mock employee when save() is called 
		when(repo.save(this.mockEmployee)).thenReturn(this.mockEmployee);
		
		// Call the service
		Integer employeeId = service.saveEmployee(this.mockEmployee);
		
		// Validate the result
		Assertions.assertEquals(1, employeeId);
	}

    /***
     * deleteEmployeeById
     * 
     * This is a method to test the EmployeeService.deleteEmployee method with a matching
     *   resource. 
     * 
     * Here's the flow:
     * - No need to setup the test data here as it is handled by the init method with the 
     *   @BeforeEach annotation.
     * - Use Mockito's when-thenReturn to intercept the call to the following JPA repository
     *   methods:
     *     - when existsById then return true.
     *     - when deleteById then do nothing.
     * - Call the EmployeeService.deleteEmployee method.
     * - Use Assertions to compare the actual result vs expected values.
     * 
     * @throws Exception
     */     
	@Test
	@DisplayName("TEST deleteEmployeeById")
	void deleteEmployeeById() throws Exception {
		// Use the mock repo to get mock a return value from existsById() and deleteById()
        when(repo.existsById(1)).thenReturn(true);
		doNothing().when(repo).deleteById(1);
		
		// Call the service
		service.deleteEmployee(1);
	}    
}
