package com.example.employee.sys.repository;

import com.example.employee.sys.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/****
 * This is the Employee Repository interface that extends the JPA repository
 * 	 library. JPA repository provides us with ready-to-use methods to perform 
 *   CRUD operations. 
 * 
 * There's no need to create an implementation of the repository and call JDBC
 *   to connect to the database and write SQL manually. This uses Hibernate to
 *   query the database.
 *   
 * Currently, H2 is the database set in this project. Even if we change the 
 *   data store configuration to use a MySQL database, nothing changes on this
 *   class. 
 * 
 * @author andie
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	/*
	 * We are not limited to the methods exposed by JPA. We can create custom
	 *   queries. Check out the official docs:
	 *   https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	 */
}
