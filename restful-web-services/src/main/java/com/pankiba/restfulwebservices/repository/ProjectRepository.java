package com.pankiba.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pankiba.restfulwebservices.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
