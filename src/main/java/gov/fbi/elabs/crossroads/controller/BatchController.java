package gov.fbi.elabs.crossroads.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.fbi.elabs.crossroads.domain.Batch;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.service.BatchService;
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

	private static final Logger logger = LoggerFactory.getLogger(BatchController.class);

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Fetch Batch Details")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "employeeId", value = "Provide Employee id whose batches need to be retrieved", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "days", value = "No of days before batch expires", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "searchTerm", value = "Provide search term", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orderBy", value = "Order by either Name or Expiry Date", dataType = "string", paramType = "query", defaultValue = "Name", allowableValues = "Name,Expires"),
			@ApiImplicitParam(name = "sortBy", value = "Sort by either ASC or DESC", dataType = "string", paramType = "query", defaultValue = "ASC", allowableValues = "ASC,DESC"),
			@ApiImplicitParam(name = "pageNum", value = "Provide Page Number", dataType = "int", paramType = "query", defaultValue = "1"),
			@ApiImplicitParam(name = "limit", value = "Provide No. of results in a payload", dataType = "int", paramType = "query", defaultValue = "10") })
	public ResponseEntity<Resources<Batch>> getBatchDetails(
			@RequestParam(value = "employeeId", required = true) Integer employeeId,
			@RequestParam(value = "days", required = true) Integer days,
			@RequestParam(value = "searchTerm", required = false) String searchTerm,
			@RequestParam(value = "orderBy", required = true, defaultValue = "Name") String orderBy,
			@RequestParam(value = "sortBy", required = true, defaultValue = "ASC") String sortBy,
			@RequestParam(value = "pageNum", required = true, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit)
			throws BaseApplicationException {

		List<Batch> batchList = batchService.getBatchDetails(employeeId, days, searchTerm, orderBy, sortBy, pageNum,
				limit);

		int results = batchList != null ? batchList.size() : 0;
		for (Batch batch : batchList) {
			batch.add(linkTo(methodOn(BatchController.class).getBatchDetails(employeeId, days, searchTerm, orderBy,
					sortBy, pageNum, limit)).withSelfRel());
		}

		Link selfLink = linkTo(methodOn(BatchController.class).getBatchDetails(employeeId, days, searchTerm, orderBy,
				sortBy, pageNum, limit)).withSelfRel();
		logger.info("No of batches returned  " + results);
		Resources<Batch> batchResources = new Resources<>(batchList, selfLink);
		return new ResponseEntity<Resources<Batch>>(batchResources, HttpStatus.OK);
	}

}
