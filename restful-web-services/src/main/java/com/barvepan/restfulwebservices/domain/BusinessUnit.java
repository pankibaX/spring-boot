package com.barvepan.restfulwebservices.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = { "employees" })
@Entity
public class BusinessUnit implements Serializable {

	private static final long serialVersionUID = -3740038521584275175L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "businessunit_id_generator")
	@SequenceGenerator(name = "businessunit_id_generator", sequenceName = "businessunit_id_seq", allocationSize = 50, initialValue = 1001)
	private Long businessUnitId;

	private String businessUnitName;

	/**
	 * OneToMany denotes that one BusinessUnit can have many Employees. Cascade means dependent object(Employee) will be
	 * persisted/updated/deleted automatically on subsequent persist/update/delete on BusinessUnit object. No need to
	 * perform operation separately on Employee.
	 * 
	 * OneToMany annotation is used to define a multi-value association to a Collection of Entities
	 * 
	 */
	@JsonManagedReference
	@OneToMany(mappedBy = "businessUnit", cascade = CascadeType.ALL)
	@ToString.Exclude
	private Set<Employee> employees = new HashSet<>();

	public BusinessUnit() {
	}

	public BusinessUnit(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}

	/**
	 * Parent side should contain the addChild and removeChild combo. These methods ensure we always synchronize both
	 * sides of the association, to avoid object or relational data corruption issues
	 */
	public void addEmployee(Employee employee) {
		employees.add(employee);
		employee.setBusinessUnit(this);
	}

	public void removeEmployee(Employee employee) {
		employees.remove(employee);
		employee.setBusinessUnit(null);
	}

}
