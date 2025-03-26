package com.pankiba.restfulwebservices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pankiba.restfulwebservices.domain.Employee;
import com.pankiba.restfulwebservices.repository.EmployeeRepository;
import com.pankiba.restfulwebservices.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

}
