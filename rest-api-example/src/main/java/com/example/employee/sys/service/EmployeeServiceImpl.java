package com.example.employee.sys.service;

import java.util.List;
import java.util.Optional;

import com.example.employee.sys.entity.Employee;
import com.example.employee.sys.exceptions.NotFoundException;
import com.example.employee.sys.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EmployeeServiceImpl
 * 
 * This is the service class that contains all the logic to handle the requests.
 *   There is not much on this sample service class. It is up to the trainee to
 *   add more content on each method to fulfill their API requirement. 
 *   
 * Any validations can be handled here. Check for conflicting records, handling
 *   of PATCH, etc.
 * 
 * @author andie
 *
 * @Service indicates that this class is a Spring component that represents the
 *   business layer of the code. This is where all business requirement logic
 *   should be done.
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository repo;
	
	public EmployeeServiceImpl() { }

	/*
	 * The following commented code are samples for the other types of 
	 *   dependency injection. The active one is using the autowiring above.
	 */
	
//	// Constructor injection
//	public EmployeeServiceImpl(EmployeeRepository repo) {
//		this.repo = repo;
//	}
	
//	// Setter injection
//  public void setRepository(EmployeeRepository repo) {
//      this.repo = repo;
//  }
	
	@Override
	public List<Employee> getEmployees() {
		return repo.findAll();
	}
	
	@Override
	public Optional<Employee> getEmployeeById(Integer id) {
		if (!repo.existsById(id))
			throw new NotFoundException(id);
		return repo.findById(id);
	}
	
	@Override
	public Integer saveEmployee(Employee employee) {
		return repo.save(employee).getEmployeeId();
	}
	
	@Override
	public void updateEmployee(Employee employee, Integer id) {
		if (!repo.existsById(id))
			throw new NotFoundException(id);
		
		// TODO handle full and partial updates
		
		employee.setEmployeeId(id);
		repo.save(employee);
	}
	
	@Override
	public void deleteEmployee(Integer id) {
		if (!repo.existsById(id))
			throw new NotFoundException(id);
		repo.deleteById(id);
	}

}
