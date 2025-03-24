package com.barvepan.restfulwebservices.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barvepan.restfulwebservices.domain.Employee;
import com.barvepan.restfulwebservices.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeResource {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * Retrieve All Employees
	 * @return
	 */
	@GetMapping(path = "/employees")
	public ResponseEntity<List<Employee>> getEmployees() {
		log.info("Fetching all employees");
		List<Employee> employees = employeeService.getEmployees();
		if (employees.isEmpty()) {
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}

}
