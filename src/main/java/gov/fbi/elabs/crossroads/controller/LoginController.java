package gov.fbi.elabs.crossroads.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath + "/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Login Controller", description = "Location Operations")
public class LoginController {
	
	@RequestMapping(value="login",method = RequestMethod.POST)
	@ApiOperation(value = "Login")
	public ResponseEntity<Object> login(@RequestHeader("username") String username, @RequestHeader("password") String password) 
			throws BaseApplicationException {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="logout",method = RequestMethod.GET)
	@ApiOperation(value = "logout")
	public ResponseEntity<Object> logout()  throws BaseApplicationException {
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
