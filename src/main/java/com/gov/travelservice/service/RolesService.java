package com.gov.travelservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gov.travelservice.entity.Roles;
import com.gov.travelservice.entity.User;
import com.gov.travelservice.repository.RolesRepository;
import com.gov.travelservice.repository.UserRepository;

@Service
@Transactional(readOnly=true)
public class RolesService {

	
	
	@Autowired
	RolesRepository rolesRepository;
	
	
	public List<Roles> findAllroles() {
		return rolesRepository.findAll();
	}
	
	
}
