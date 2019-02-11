package gov.fbi.elabs.crossroads.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.fbi.elabs.crossroads.domain.EmployeeAuth;
import gov.fbi.elabs.crossroads.domain.User;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.utilities.Constants;
import gov.fbi.elabs.crossroads.utilities.EmployeeAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath + "/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Login Controller", description = "Login/Logout Operations")
public class LoginController {

	@Autowired
	private EmployeeAuthUtil employeeAuthUtil;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ApiOperation(value = "Login")
	@ApiImplicitParam(name = "Authorization", value = "Authentication Basic Auth", paramType = "header", dataType = "string", required = true)
	public ResponseEntity<User> login(HttpSession session) throws BaseApplicationException {
		String sessionId = session.getId();

		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		EmployeeAuth employeeAuth = employeeAuthUtil.getEmployeeAuthDetails(username);

		if (employeeAuth.getEmployeeId() == null
				|| !CollectionUtils.containsAny(employeeAuth.getRoleList(), Constants.ROLES)) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}

		// session.setAttribute("roles", employeeAuth);

		User user = new User();
		user.setSessionId(sessionId);
		user.setUsername(employeeAuth.getDisplayName());

		System.out.println("Login Session ID " + sessionId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "logout", method = RequestMethod.POST)
	@ApiOperation(value = "logout")
	public ResponseEntity<Object> logout(HttpServletRequest request) throws BaseApplicationException {
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		if (session != null) {
			session.invalidate();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = { "text/plain" })
	@ApiOperation(value = "test")
	public ResponseEntity<String> test(HttpSession session) throws BaseApplicationException {
		try {
			String user = SecurityContextHolder.getContext().getAuthentication().getName();
			return new ResponseEntity<>(user + ":" + session.getId(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error in getting Auth Context..", e);
			return new ResponseEntity<>(session.getId(), HttpStatus.OK);
		}
	}

}
