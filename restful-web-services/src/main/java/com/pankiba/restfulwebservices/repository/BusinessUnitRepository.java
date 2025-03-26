package com.pankiba.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pankiba.restfulwebservices.domain.BusinessUnit;

public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {

}
