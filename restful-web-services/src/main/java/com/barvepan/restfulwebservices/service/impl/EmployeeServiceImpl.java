package com.barvepan.restfulwebservices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barvepan.restfulwebservices.domain.Employee;
import com.barvepan.restfulwebservices.repository.EmployeeRepository;
import com.barvepan.restfulwebservices.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

}
