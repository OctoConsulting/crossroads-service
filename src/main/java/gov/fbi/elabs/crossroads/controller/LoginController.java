package gov.fbi.elabs.crossroads.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
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
	
	@Autowired
	RedisOperationsSessionRepository redisRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@RequestMapping(value="login",method = RequestMethod.POST)
	@ApiOperation(value = "Login")
	public ResponseEntity<String> login(HttpSession session) 
			throws BaseApplicationException {
		String sessionId = session.getId();
		System.out.println("Login Session ID "+sessionId);
		System.out.println("Redis Session Created >> "+redisRepo.getSession(sessionId));
		return new ResponseEntity<String>(sessionId,HttpStatus.OK);
	}
	
	@RequestMapping(value="signout",method = RequestMethod.POST)
	@ApiOperation(value = "logout")
	public ResponseEntity<Object> logout(HttpSession session)  throws BaseApplicationException {
		System.out.println("Redis Session Before invalidate >> "+redisRepo.getSession(session.getId()));
		session.invalidate();
		SecurityContextHolder.clearContext();
		System.out.println("Redis Session After invalidate>> "+redisRepo.getSession(session.getId()));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="test",method = RequestMethod.GET)
	@ApiOperation(value = "test")
	public ResponseEntity<String> test(HttpSession session)  throws BaseApplicationException {
		System.out.println("Login Session "+session.getId());
		try {
			String user  = SecurityContextHolder.getContext().getAuthentication().getName();
			logger.info("Login User Session "+user);
			return new ResponseEntity<>(user+":"+session.getId(),HttpStatus.OK);
		}catch (Exception e) {
			logger.error("Error in getting Auth Context..",e);
			return new ResponseEntity<>(session.getId(),HttpStatus.OK);
		}
	}

}
