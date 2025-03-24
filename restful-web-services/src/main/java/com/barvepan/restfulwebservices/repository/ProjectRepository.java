package com.barvepan.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barvepan.restfulwebservices.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
