package com.barvepan.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barvepan.restfulwebservices.domain.BusinessUnit;

public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {

}
