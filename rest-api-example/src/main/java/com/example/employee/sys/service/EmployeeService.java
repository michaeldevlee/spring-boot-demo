package com.example.employee.sys.service;

import java.util.List;
import java.util.Optional;

import com.example.employee.sys.entity.Employee;

public interface EmployeeService {

	List<Employee> getEmployees();
	Optional<Employee> getEmployeeById(Integer id);
	Integer saveEmployee(Employee employee);
	void updateEmployee(Employee employee, Integer id);
	void deleteEmployee(Integer id);
}
