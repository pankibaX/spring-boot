package com.pankiba.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pankiba.restfulwebservices.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
