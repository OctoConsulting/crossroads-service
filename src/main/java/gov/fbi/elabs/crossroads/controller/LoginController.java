package gov.fbi.elabs.crossroads.controller;


import javax.servlet.http.HttpSession;


import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath + "/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Login Controller", description = "Login/Logout Operations")
public class LoginController {
	
	@RequestMapping(value="login",method = RequestMethod.POST)
	@ApiOperation(value = "Login")
	public ResponseEntity<String> login(HttpSession session) 
			throws BaseApplicationException {
		System.out.println("Login Session "+session.getId());
		return new ResponseEntity<String>(session.getId(),HttpStatus.OK);
	}
	
	@RequestMapping(value="logout",method = RequestMethod.POST)
	@ApiOperation(value = "logout")
	public ResponseEntity<Object> logout()  throws BaseApplicationException {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="test",method = RequestMethod.GET)
	@ApiOperation(value = "test")
	public ResponseEntity<String> test(HttpSession session)  throws BaseApplicationException {
		System.out.println("Login Session "+session.getId());
		String user  = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("Login Session "+user);
		return new ResponseEntity<>(user+":"+session.getId(),HttpStatus.OK);
	}

}
