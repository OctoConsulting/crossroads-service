package com.gov.travelservice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gov.travelservice.entity.Roles;
import com.gov.travelservice.entity.User;
import com.gov.travelservice.repository.RolesRepository;
import com.gov.travelservice.repository.UserRepository;

@Service
@Transactional(readOnly=true)
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RolesRepository rolesRepository;
	
	public User findByUserId(long userId) {
		return userRepository.findById(userId);
	}

	public User authenticateuser(String username) {
		return userRepository.findByUsername(username);
	}

	public void resetPassword(long userid, String password) {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		userRepository.resetPassword(userid,b.encode(password));
	}

	public List<Roles> findAll() {
		return rolesRepository.findAll();
	}

	/**
	 * Method to create user.
	 * @param userBo
	 */
	@Transactional(readOnly=false)
	public User createuser(User user) {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		user.setPassword(b.encode("resetpassword"));
		user.setCreated_date(new java.sql.Date(System.currentTimeMillis()));
		user.setSupervisor(user.getSupervisor());
		user.settRoleId(user.gettRoleId());
		return userRepository.saveAndFlush(user);
	 
	}
	
	
}
