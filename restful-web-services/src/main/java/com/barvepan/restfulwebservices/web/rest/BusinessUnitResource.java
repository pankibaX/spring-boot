package com.barvepan.restfulwebservices.web.rest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.barvepan.restfulwebservices.domain.BusinessUnit;
import com.barvepan.restfulwebservices.domain.Employee;
import com.barvepan.restfulwebservices.service.BusinessUnitService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/business-units")
@Slf4j
public class BusinessUnitResource {

	@Autowired
	private BusinessUnitService businessUnitService;

	/**
	 * Retrieve All BusinessUnits
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<BusinessUnit>> getBusinessUnits() {
		
		log.info("Fetching all business units");
		List<BusinessUnit> businessUnits = businessUnitService.getBusinessUnits();
		if (businessUnits.isEmpty()) {
			return new ResponseEntity<List<BusinessUnit>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BusinessUnit>>(businessUnits, HttpStatus.OK);
	}

	/**
	 * Retrieve BusinessUnit
	 * 
	 * @param businessUnitId
	 * @return
	 */
	@GetMapping(path = "/{businessUnitId}")
	public ResponseEntity<BusinessUnit> getBusinessUnit(@PathVariable Long businessUnitId) {
		log.info("Fetching BusinessUnit with id {}", businessUnitId);
		BusinessUnit businessUnit = businessUnitService.getBusinessUnit(businessUnitId);
		return new ResponseEntity<BusinessUnit>(businessUnit, HttpStatus.OK);
	}
	
	/**
	 * Create BusinessUnit
	 * 
	 * @param businessUnit
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Object> createBusinessUnit(@RequestBody BusinessUnit businessUnit) {
		log.info("Creating BusinessUnit : {}", businessUnit);
		BusinessUnit savedBusinessUnit = businessUnitService.createBusinessUnit(businessUnit);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{businessunitId}")
				.buildAndExpand(savedBusinessUnit.getBusinessUnitId()).toUri();
		return ResponseEntity.created(location).body(savedBusinessUnit);
	}
	
	/**
	 * Create BusinessUnits in Bulk
	 * 
	 * @param businessUnit
	 * @return
	 */
	@PostMapping(path = "/bulk")
	public ResponseEntity<List<BusinessUnit>> createBusinessUnits(@RequestBody List<BusinessUnit> businessUnits) {
		
		List<BusinessUnit> savedBusinessUnits = new ArrayList<>();
		
		for (BusinessUnit businessUnit : businessUnits) {
			log.info("Creating BusinessUnit : {}", businessUnit);
			BusinessUnit savedBusinessUnit = businessUnitService.createBusinessUnit(businessUnit);
			savedBusinessUnits.add(savedBusinessUnit);
		}
		
		return ResponseEntity.ok(savedBusinessUnits);

	}

	/**
	 * Update BusinessUnit
	 * 
	 * @param businessUnitId
	 * @param businessUnit
	 * @return
	 */
	@PutMapping(path = "/{businessUnitId}")
	public ResponseEntity<BusinessUnit> updateBusinessUnit(@PathVariable Long businessUnitId,
			@RequestBody BusinessUnit businessUnit) {
		log.info("Updating BusinessUnit with id {}", businessUnitId);
		BusinessUnit updatedBusinessUnit = businessUnitService.updateBusinessUnit(businessUnitId, businessUnit);
		return new ResponseEntity<BusinessUnit>(updatedBusinessUnit, HttpStatus.OK);
	}

	/**
	 * Remove Business Unit
	 * 
	 * @param businessUnitId
	 */
	@DeleteMapping(path = "/{businessUnitId}")
	public ResponseEntity<BusinessUnit> deleteBusinessUnit(@PathVariable Long businessUnitId) {
		log.info("Fetching & Deleting BusinessUnit with id {}", businessUnitId);
		businessUnitService.deleteBusinessUnit(businessUnitId);
		return new ResponseEntity<BusinessUnit>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Retrieve All Employees for single BusinessUnit
	 * 
	 * @param businessUnitId
	 * @return
	 */
	@GetMapping(path = "/{businessUnitId}/employees")
	public ResponseEntity<List<Employee>> getEmployees(@PathVariable Long businessUnitId) {
		log.info("Fetching all Employees for BusinessUnit {} ", businessUnitId);
		return new ResponseEntity<List<Employee>>(businessUnitService.getEmployees(businessUnitId), HttpStatus.OK);
	}

	/**
	 * Create Employee for BusinessUnit
	 * 
	 * @param businessUnitId
	 * @param employee
	 * @return
	 */
	@PostMapping(path = "/{businessUnitId}/employees")
	public ResponseEntity<Object> createBusinessUnit(@PathVariable Long businessUnitId,
			@Valid @RequestBody Employee employee) {
	
		log.info("Create Employee for BusinessUnit : {}", employee);
		Employee savedEmployee = businessUnitService.createEmployeeForBusinessUnit(businessUnitId, employee);
		log.info("{}",savedEmployee);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{employeeId}")
				.buildAndExpand(savedEmployee.getEmployeeId()).toUri();
		return ResponseEntity.created(location).body(savedEmployee);
	}

	/**
	 * Retrieve details of employee for BusinessUnit
	 * 
	 * @param businessUnitId
	 * @return
	 */
	@GetMapping(path = "/{businessUnitId}/employees/{employeeId}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Long businessUnitId, @PathVariable Long employeeId) {
		log.info("Fetching Employee details of {} for BusinessUnit {} ", employeeId, businessUnitId);
		return new ResponseEntity<Employee>(businessUnitService.getEmployee(businessUnitId, employeeId), HttpStatus.OK);
	}

}
