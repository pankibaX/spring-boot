package com.pankiba.restfulwebservices.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = { "employeeProfile" })
@NoArgsConstructor
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeid_generator")
	@SequenceGenerator(name = "employeeid_generator", sequenceName = "emp_id_seq", allocationSize = 50, initialValue = 5001)
	private Long employeeId;

	private String firstName;

	private String lastName;

	private String gender;

	@Column(unique = true)
	private String email;

	private LocalDate birthDate;

	private LocalDate joiningDate;

	@Enumerated(EnumType.STRING)
	private Grade grade;

	private Long salary;

	/**
	 * To declare a side as not responsible for the relationship, the attribute
	 * mappedBy is used. mappedBy refers to the property name of the association on
	 * the owner side. Here, EmployeeDetails entity is owner. mappedBy attribute
	 * declares that it is dependent on owner entity for mapping.
	 *
	 * @OneToOne defines a one-to-one relationship between 2 entities. mappedBy
	 *           indicates the inverse (non-owning) of the relationship.
	 */
	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
	private EmployeeProfile employeeProfile;

	/**
	 * JoinColumn says that Employee table will contain a separate column
	 * BUSINESS_UNIT_ID which will act as a foreign key reference to primary key of
	 * BUSINESS_UNIT table.
	 *
	 * ManyToOne annotation is used to define a single value association with
	 * another entity bean.
	 *
	 * Many to one side is the owning side of the relationship. Owning side of the
	 * relation tracked by Hibernate is the side of the relation that owns the
	 * foreign key in the database.
	 */
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "BUSINESS_UNIT_ID")
	private BusinessUnit businessUnit;

	/**
	 * 
	 * many-to-many database association includes two parent tables which are linked
	 * through a third one containing two Foreign Keys referencing the parent
	 * tables.
	 * 
	 * ManyToMany indicates that there is a Many-to-Many relationship between
	 * Employee and Project. A employee can have multiple projects, and a project
	 * can have multiple Employees enrolled.
	 *
	 * JoinTable indicates that there is a link table which joins two tables via
	 * containing there keys. joinColumns refers to the column name of owning
	 * side(EMPLOYEE_ID of EMPLOYEE), and inverseJoinColumns refers to the column of
	 * inverse side of relationship(PROJECT_ID of PROJECT). Primary key of this
	 * joined table is combination of EMPLOYEE_ID & PROJECT_ID.
	 */

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "EMPLOYEE_PROJECT", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"), inverseJoinColumns = @JoinColumn(name = "PROJECT_ID"))
	private Set<Project> projects = new HashSet<Project>();

	public Employee(String firstName, String lastName, String gender, String email, LocalDate birthDate,
			LocalDate joiningDate, Grade grade, Long salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.birthDate = birthDate;
		this.joiningDate = joiningDate;
		this.grade = grade;
		this.salary = salary;
	}

	/*
	 * add/remove utility methods are mandatory if you use bidirectional
	 * associations so that you can make sure that both sides of the association are
	 * in sync
	 */
	public void addProject(Project project) {
		projects.add(project);
		project.getEmployees().add(this);
	}

	public void removeProject(Project project) {
		projects.remove(project);
		project.getEmployees().remove(this);
	}
}
