package com.pankiba.restfulwebservices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = { "employee" })
@NoArgsConstructor
@Entity
public class EmployeeProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String address;
	private String phoneNumber;

	/**
	 *
	 * @JoinColumn defines foreign key column and indicates the owner of the
	 *             relationship.
	 */

	
	@OneToOne(cascade = CascadeType.ALL) 
	@JoinColumn(unique = true, name = "EMPLOYEE_ID")
	@JsonBackReference
	private Employee employee;
	
	public EmployeeProfile(String address, String phoneNumber) {
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
}
