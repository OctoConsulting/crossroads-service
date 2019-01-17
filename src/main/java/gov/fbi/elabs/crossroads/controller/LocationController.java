package gov.fbi.elabs.crossroads.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.fbi.elabs.crossroads.domain.EmployeeAuth;
import gov.fbi.elabs.crossroads.domain.Location;
import gov.fbi.elabs.crossroads.domain.Organization;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.service.LocationService;
import gov.fbi.elabs.crossroads.utilities.Constants;
import gov.fbi.elabs.crossroads.utilities.EmployeeAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath + "/v1/location", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Location", description = "Location Operations")
public class LocationController {

	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

	@Autowired
	private LocationService locationService;

	@Autowired
	private EmployeeAuthUtil employeeAuthUtil;

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Fetch all workflow")
	public List<Location> getAllLocations() throws BaseApplicationException {
		return locationService.getAllLocations();
	}

	@RequestMapping(value = "/AtLab", method = RequestMethod.GET)
	@ApiOperation(value = "Fetch At Lab information for the logged in user")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "status", value = "Provide status of the Transfer Type", dataType = "string", paramType = "query", allowableValues = "Everything,Active,Inactive", defaultValue = "Everything"),
			@ApiImplicitParam(name = "x-auth-token", value = "Authentication Token", paramType = "header", dataType = "string", required = true) })
	public ResponseEntity<Resources<Location>> getAtLabInfo(
			@RequestParam(value = "status", required = true, defaultValue = "Everything") String status,
			HttpServletRequest request) throws BaseApplicationException {

		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		EmployeeAuth employeeAuth = employeeAuthUtil.getEmployeeAuthDetails(username);

		if (employeeAuth.getEmployeeId() == null
				|| !CollectionUtils.containsAny(employeeAuth.getRoleList(), Constants.ROLES)
				|| !(employeeAuth.getTaskList().containsAll(Constants.TASKS))) {
			return new ResponseEntity<Resources<Location>>(HttpStatus.UNAUTHORIZED);
		}

		List<Location> locList = locationService.getLabInformation(employeeAuth.getEmployeeId(), status);
		int res = locList != null ? locList.size() : 0;
		logger.info("No of results returned " + res);

		for (Location loc : locList) {
			loc.add(linkTo(methodOn(LocationController.class).getAtLabInfo(status, request)).withSelfRel());
		}
		Link selfLink = linkTo(methodOn(LocationController.class).getAtLabInfo(status, request)).withSelfRel();
		Resources<Location> locResource = new Resources<>(locList, selfLink);

		return new ResponseEntity<Resources<Location>>(locResource, HttpStatus.OK);

	}

	@RequestMapping(value = "/AtUnit", method = RequestMethod.GET)
	@ApiOperation(value = "Fetch At Unit information for the logged in user")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "locationId", value = "Provide locationId of the at Lab selected", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "status", value = "Provide status of the Transfer Type", dataType = "string", paramType = "query", allowableValues = "Everything,Active,Inactive", defaultValue = "Everything"),
			@ApiImplicitParam(name = "x-auth-token", value = "Authentication Token", paramType = "header", dataType = "string", required = true) })
	public ResponseEntity<Resources<Organization>> getAtUnitInfo(
			@RequestParam(value = "locationId", required = true) Integer locationId,
			@RequestParam(value = "status", required = true) String status, HttpServletRequest request)
			throws BaseApplicationException {

		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		EmployeeAuth employeeAuth = employeeAuthUtil.getEmployeeAuthDetails(username);

		if (employeeAuth.getEmployeeId() == null
				|| !CollectionUtils.containsAny(employeeAuth.getRoleList(), Constants.ROLES)
				|| !(employeeAuth.getTaskList().containsAll(Constants.TASKS))) {
			return new ResponseEntity<Resources<Organization>>(HttpStatus.UNAUTHORIZED);
		}

		List<Organization> orgList = locationService.getUnitInformation(employeeAuth.getEmployeeId(), locationId,
				status);
		int res = orgList != null ? orgList.size() : 0;
		logger.info("No of orgs " + res);

		for (Organization org : orgList) {
			org.add(linkTo(methodOn(LocationController.class).getAtUnitInfo(locationId, status, request))
					.withSelfRel());
		}

		Link selfLink = linkTo(methodOn(LocationController.class).getAtUnitInfo(locationId, status, request))
				.withSelfRel();
		Resources<Organization> orgsResource = new Resources<>(orgList, selfLink);
		return new ResponseEntity<Resources<Organization>>(orgsResource, HttpStatus.OK);

	}

}
