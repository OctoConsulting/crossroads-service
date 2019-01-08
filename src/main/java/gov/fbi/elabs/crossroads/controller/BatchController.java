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
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.fbi.elabs.crossroads.domain.Batch;
import gov.fbi.elabs.crossroads.domain.BatchDetails;
import gov.fbi.elabs.crossroads.domain.EmployeeAuth;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.service.BatchService;
import gov.fbi.elabs.crossroads.utilities.Constants;
import gov.fbi.elabs.crossroads.utilities.EmployeeAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath + "/v1/batch", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Batch", description = "Batch Operations")
public class BatchController {

	@Autowired
	private BatchService batchService;

	@Autowired
	private EmployeeAuthUtil employeeAuthUtil;

	private static final Logger logger = LoggerFactory.getLogger(BatchController.class);

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Fetch Batch Details")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "days", value = "No of days before batch expires", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "searchTerm", value = "Provide search term", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orderBy", value = "Order by either Name or Expiry Date", dataType = "string", paramType = "query", defaultValue = "Name", allowableValues = "Name,Expires"),
			@ApiImplicitParam(name = "sortBy", value = "Sort by either ASC or DESC", dataType = "string", paramType = "query", defaultValue = "ASC", allowableValues = "ASC,DESC"),
			@ApiImplicitParam(name = "pageNum", value = "Provide Page Number", dataType = "int", paramType = "query", defaultValue = "1"),
			@ApiImplicitParam(name = "limit", value = "Provide No. of results in a payload", dataType = "int", paramType = "query", defaultValue = "10"),
			@ApiImplicitParam(name = "X-Auth-Token", value = "Authentication Token", paramType = "header", dataType = "string", required = true) })
	public ResponseEntity<Resource<BatchDetails>> getBatchDetails(
			@RequestParam(value = "days", required = true) Integer days,
			@RequestParam(value = "searchTerm", required = false) String searchTerm,
			@RequestParam(value = "orderBy", required = true, defaultValue = "Name") String orderBy,
			@RequestParam(value = "sortBy", required = true, defaultValue = "ASC") String sortBy,
			@RequestParam(value = "pageNum", required = true, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
			HttpServletRequest request) throws BaseApplicationException {

		String username = (String) request.getAttribute("username");
		EmployeeAuth employeeAuth = employeeAuthUtil.getEmployeeAuthDetails(username);

		if (employeeAuth.getEmployeeId() == null
				|| !CollectionUtils.containsAny(employeeAuth.getRoleList(), Constants.ROLES)
				|| !employeeAuth.getTaskList().contains(Constants.CAN_VIEW_BATCH)) {
			return new ResponseEntity<Resource<BatchDetails>>(HttpStatus.UNAUTHORIZED);
		}

		BatchDetails details = batchService.getBatchDetails(employeeAuth.getEmployeeId(), days, searchTerm, orderBy,
				sortBy, pageNum, limit);

		List<Batch> batchList = details.getBatchList();

		int results = batchList != null ? batchList.size() : 0;
		for (Batch batch : batchList) {
			batch.add(linkTo(methodOn(BatchController.class).getBatchDetails(days, searchTerm, orderBy, sortBy, pageNum,
					limit, request)).withSelfRel());
		}

		Link selfLink = linkTo(methodOn(BatchController.class).getBatchDetails(days, searchTerm, orderBy, sortBy,
				pageNum, limit, request)).withSelfRel();
		logger.info("No of batches returned " + results);
		Resource<BatchDetails> batchResources = new Resource<>(details, selfLink);
		return new ResponseEntity<Resource<BatchDetails>>(batchResources, HttpStatus.OK);
	}

}
