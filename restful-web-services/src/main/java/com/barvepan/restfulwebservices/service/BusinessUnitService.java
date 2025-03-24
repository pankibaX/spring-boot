package com.barvepan.restfulwebservices.service;

import java.util.List;

import com.barvepan.restfulwebservices.domain.BusinessUnit;
import com.barvepan.restfulwebservices.domain.Employee;

public interface BusinessUnitService {

	public List<BusinessUnit> getBusinessUnits();

	public BusinessUnit createBusinessUnit(BusinessUnit businessUnit);

	public BusinessUnit getBusinessUnit(Long businessUnitId);

	public BusinessUnit updateBusinessUnit(Long businessUnitId, BusinessUnit businessUnit);

	public void deleteBusinessUnit(Long businessUnitId);

	public List<Employee> getEmployees(Long businessUnitId);

	public Employee createEmployeeForBusinessUnit(Long businessUnitId, Employee employee);

	public Employee getEmployee(Long businessUnitId, Long employeeId);
	

}
