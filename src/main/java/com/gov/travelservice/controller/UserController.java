package com.gov.travelservice.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gov.travelservice.entity.Roles;
import com.gov.travelservice.entity.User;
import com.gov.travelservice.service.RolesService;
import com.gov.travelservice.service.UserService;


@RestController
@RequestMapping(value =  "travelservice/v1/", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RolesService rolesService;
	
	
	
	@Autowired
	AuthenticationManager authenticationManager;

	/**
	 * Method to get user details
	 * @param userid
	 * @return
	 */
	//ResponseEntity<Resource<TravelRecord>>
	@RequestMapping(value = "user/{userid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User getTravelRecords(@PathVariable("userid") long userid) {
		return  userService.findByUserId(userid);
	}
	
	
	/**
	 * Method to rest user password
	 * @param password
	 * @param userid
	 */
	@RequestMapping(value = "user/reset/{userid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getTravelRecords(@RequestHeader(value="password") String password
		           ,@PathVariable("userid") long userid) {
		 userService.resetPassword(userid,password);
	}
	
	/**
	 * Method to get all roles
	 * @return
	 */
	@RequestMapping(value = "roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Roles> getAllRoles(){
		List<Roles> roles =  rolesService.findAllroles();
		System.out.println(roles.size());	
		return roles;
	}
	
	/**
	 * API to create user. This will be only for Admin.
	 * @param userBo
	 * @return
	 */
	@RequestMapping(value = "createuser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User createuser(@RequestBody User user){
		return userService.createuser(user);
	}

		
		
		
		
	/**
	 * Authenticate user
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "authenticate/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> authenticateUser(@RequestHeader(value="username") String username
			           ,@RequestHeader(value="password") String password) {
		
	    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username,password);
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(authReq);			
		} catch(BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"loginResponse\" : \"Invalid username or password\" }");
		}

		boolean isAuthenticated = isAuthenticated(authentication);
	    if (isAuthenticated) {
	        SecurityContextHolder.getContext().setAuthentication(authentication);
			User user =  userService.authenticateuser(username);
		    return ResponseEntity.status(HttpStatus.OK).body(user);
	    } else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authentication);
	    }
/*		User user =  userService.authenticateuser(username,password);
		if(user == null) {
			 return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		} else {
			return user;
		}
*/
	}
	
	

	private boolean isAuthenticated(Authentication authentication) {
		return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}

}
