package com.pankiba.restfulwebservices.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pankiba.restfulwebservices.domain.BusinessUnit;
import com.pankiba.restfulwebservices.domain.Employee;
import com.pankiba.restfulwebservices.exception.ResourceNotFoundException;
import com.pankiba.restfulwebservices.repository.BusinessUnitRepository;
import com.pankiba.restfulwebservices.repository.EmployeeRepository;
import com.pankiba.restfulwebservices.service.BusinessUnitService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BusinessUnitServiceImpl implements BusinessUnitService {

	@Autowired
	private BusinessUnitRepository businessUnitRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<BusinessUnit> getBusinessUnits() {
		return businessUnitRepository.findAll();
	}

	@Override
	public BusinessUnit createBusinessUnit(BusinessUnit businessUnit) {
		
		businessUnit.getEmployees().forEach(employee -> {
			employee.setBusinessUnit(businessUnit);
			if(employee.getEmployeeProfile() != null) {
                employee.setEmployeeProfile(employee.getEmployeeProfile());
                employee.getEmployeeProfile().setEmployee(employee);
			}
		});
		
		
		return businessUnitRepository.save(businessUnit);
	}

	@Override
	public BusinessUnit getBusinessUnit(Long businessUnitId) {
		Optional<BusinessUnit> businessUnitOptional = validateBusinessUnit(businessUnitId);
		return businessUnitOptional.get();
	}

	@Override
	public BusinessUnit updateBusinessUnit(Long businessUnitId, BusinessUnit businessUnit) {
		validateBusinessUnit(businessUnitId);
		businessUnit.setBusinessUnitId(businessUnitId);
		return businessUnitRepository.save(businessUnit);
	}

	@Override
	public void deleteBusinessUnit(Long businessUnitId) {
		Optional<BusinessUnit> businessUnitOptional = validateBusinessUnit(businessUnitId);
		businessUnitRepository.delete(businessUnitOptional.get());
	}

	private Optional<BusinessUnit> validateBusinessUnit(Long businessUnitId) {
		Optional<BusinessUnit> businessUnitOptional = businessUnitRepository.findById(businessUnitId);

		if (!businessUnitOptional.isPresent()) {
			log.error("BusinessUnit with id {} not found.", businessUnitId);
			throw new ResourceNotFoundException(BusinessUnit.class, businessUnitId.toString());
		}
		return businessUnitOptional;
	}

	@Override
	public List<Employee> getEmployees(Long businessUnitId) {
		BusinessUnit businessUnit = getBusinessUnit(businessUnitId);
		List<Employee> employees = new ArrayList<Employee>();
		employees.addAll(businessUnit.getEmployees());
		return employees;
	}

	@Override
	public Employee createEmployeeForBusinessUnit(Long businessUnitId, Employee employee) {
		Optional<BusinessUnit> businessUnitOptional = validateBusinessUnit(businessUnitId);
		employee.setBusinessUnit(businessUnitOptional.get());
		return employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployee(Long businessUnitId, Long employeeId) {
		Optional<BusinessUnit> businessUnitOptional = validateBusinessUnit(businessUnitId);
		Set<Employee> employees = businessUnitOptional.get().getEmployees();
		return employees.stream().filter(emp -> emp.getEmployeeId().equals(employeeId)).findFirst().get();
	}

}
