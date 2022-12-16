package com.example.employee.sys.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/****
 * Employee model
 * 
 * This represents the data that the API consumers will get.
 * 
 * A couple of JPA annotations are used here:
 * 
 * 1. @Entity is used to annotate that this class represents data that can be 
 *   persisted to a database.
 *   
 * 2. @Table specifies the name of the table in the database.
 * 
 * 3. @Id marks the variable as the primary key for the entity. This works in
 *   tandem with the @GeneratedValue annotation which indicates how the ID is to
 *   be generated.
 * 
 * @author andie
 *
 */
@Entity
@Table(name = "employees")
public class Employee {

	/*
	 * The employeeId variable
	 * 
	 * The GenerationType.IDENTITY indicates that the database will generate the
	 *   ID and will auto-increment it.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int employeeId;

	/*
	 * The @Null and @NotBlank annotations are part of the Hibernate Validator
	 *   library included in spring-boot-validation-starter dependency. These
	 *   are used to validate the entity field they are attached to. 
	 */
	@NotNull(message = "The employeeName field should not be null.")
	@NotBlank(message = "The employeeName should not be blank.")
	private String employeeName;
	
	private String position;
	
	/*
	 * @JsonFormat is used to define the date format during serialization.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
    // @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	private Date dateHired;

	/*
	 * Constructors: empty and all arguments
	 */
	
	public Employee() {}
	
	public Employee(int employeeId, String employeeName, String position, Date dateHired) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.position = position;
		this.dateHired = dateHired;
	}
	
	/*
	 * Getter and Setter methods
	 */
	
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Date getDateHired() {
		return dateHired;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

}
