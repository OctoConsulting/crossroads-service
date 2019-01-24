package gov.fbi.elabs.crossroads.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
			@ApiImplicitParam(name = "x-auth-token", value = "x-auth-token", dataType = "string", paramType = "header", required = true) })
	public ResponseEntity<Resource<BatchDetails>> getBatchDetails(
			@RequestParam(value = "days", required = true) Integer days,
			@RequestParam(value = "searchTerm", required = false) String searchTerm,
			@RequestParam(value = "orderBy", required = true, defaultValue = "Name") String orderBy,
			@RequestParam(value = "sortBy", required = true, defaultValue = "ASC") String sortBy,
			@RequestParam(value = "pageNum", required = true, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit)
			throws BaseApplicationException {

		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
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
			batch.add(linkTo(
					methodOn(BatchController.class).getBatchDetails(days, searchTerm, orderBy, sortBy, pageNum, limit))
							.withSelfRel());
		}

		Link selfLink = linkTo(
				methodOn(BatchController.class).getBatchDetails(days, searchTerm, orderBy, sortBy, pageNum, limit))
						.withSelfRel();

		List<Link> linkList = new ArrayList<>();
		linkList.add(selfLink);
		if (employeeAuthUtil.checkTaskPerm(Constants.CAN_TRANSFER_BATCH) && results > 0) {
			Link transferBatchLink = linkTo(
					methodOn(BatchController.class).getBatchDetails(days, searchTerm, orderBy, sortBy, pageNum, limit))
							.withRel(Constants.TRANSFER);
			linkList.add(transferBatchLink);
		}

		logger.info("No of batches returned " + results);
		Resource<BatchDetails> batchResources = new Resource<>(details, linkList);
		return new ResponseEntity<Resource<BatchDetails>>(batchResources, HttpStatus.OK);
	}

}
