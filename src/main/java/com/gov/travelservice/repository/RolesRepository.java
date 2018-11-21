package com.gov.travelservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gov.travelservice.entity.Roles;
import com.gov.travelservice.entity.User;

public interface RolesRepository extends JpaRepository<Roles, Long>, JpaSpecificationExecutor<Roles> {
	public User findById(long id);

	
	public List<Roles> findAll();
	

	
}
