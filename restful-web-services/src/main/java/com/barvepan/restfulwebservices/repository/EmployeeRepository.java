package com.barvepan.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barvepan.restfulwebservices.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
