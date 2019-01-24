package gov.fbi.elabs.crossroads.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import gov.fbi.elabs.crossroads.domain.Evidence;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.service.EvidenceService;
import gov.fbi.elabs.crossroads.utilities.Constants;
import gov.fbi.elabs.crossroads.utilities.EmployeeAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath + "/v1/evidence", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Evidence", description = "Evidence Operations")
public class EvidenceController {

	@Autowired
	private EvidenceService evidenceService;

	@Autowired
	private EmployeeAuthUtil employeeAuthUtil;

	private static final Logger logger = LoggerFactory.getLogger(EvidenceController.class);

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Fetch Evidence Details for Batch Id")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "batchId", value = "Provide Batch id for which evidences to be retrieved", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "hierarchy", value = "Provide true if hierarchy results to be returned too", dataType = "boolean", paramType = "query", required = true, allowableValues = "true,false"),
			@ApiImplicitParam(name = "x-auth-token", value = "Authentication Token", paramType = "header", dataType = "string", required = true) })
	public ResponseEntity<Resources<Evidence>> getEvidenceForBatch(
			@RequestParam(value = "batchId", required = true) Integer batchId,
			@RequestParam(value = "hierarchy", required = true) Boolean hierarchy, HttpServletRequest request)
			throws BaseApplicationException {

		if (!employeeAuthUtil.checkRoleTasks(request)) {
			return new ResponseEntity<Resources<Evidence>>(HttpStatus.UNAUTHORIZED);
		}

		List<Evidence> evidenceList = evidenceService.getEvidenceListForBatch(batchId, hierarchy);
		int results = evidenceList != null ? evidenceList.size() : 0;

		for (Evidence evidence : evidenceList) {
			evidence.add(linkTo(methodOn(EvidenceController.class).getEvidenceForBatch(batchId, hierarchy, request))
					.withSelfRel());
		}

		List<Link> linkList = new ArrayList<>();
		if (employeeAuthUtil.checkTaskPerm(Constants.CAN_TRANSFER_BATCH)) {
			Link transferBatchLink = linkTo(
					methodOn(EvidenceController.class).getEvidenceForBatch(batchId, hierarchy, request))
							.withRel(Constants.TRANSFER);
			linkList.add(transferBatchLink);
		}

		Link selfLink = linkTo(methodOn(EvidenceController.class).getEvidenceForBatch(batchId, hierarchy, request))
				.withSelfRel();
		linkList.add(selfLink);

		Resources<Evidence> evidenceResources = new Resources<>(evidenceList, linkList);
		logger.info("No of evidence Returned " + results);
		return new ResponseEntity<Resources<Evidence>>(evidenceResources, HttpStatus.OK);
	}

	@RequestMapping(value = "/hierarchy", method = RequestMethod.GET)
	@ApiOperation(value = "Fetch Evidence Details for evidenceSubmissionId")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "evidenceSubmissionId", value = "Provide evidenceSubmissionId of the parent", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "x-auth-token", value = "Authentication Token", paramType = "header", dataType = "string", required = true) })
	public ResponseEntity<Resources<Evidence>> getEvidenceHierarchyDetails(
			@RequestParam(value = "evidenceSubmissionId", required = true) Integer evidenceSubmissionId,
			HttpServletRequest request) throws BaseApplicationException {

		if (!employeeAuthUtil.checkRoleTasks(request)) {
			return new ResponseEntity<Resources<Evidence>>(HttpStatus.UNAUTHORIZED);
		}

		List<Evidence> evidenceList = evidenceService.getEvidenceHierarchy(evidenceSubmissionId);
		int results = evidenceList != null ? evidenceList.size() : 0;

		for (Evidence evidence : evidenceList) {
			evidence.add(linkTo(
					methodOn(EvidenceController.class).getEvidenceHierarchyDetails(evidenceSubmissionId, request))
							.withSelfRel());
		}

		List<Link> linkList = new ArrayList<>();
		if (employeeAuthUtil.checkTaskPerm(Constants.CAN_TRANSFER_BATCH)) {
			Link transferBatchLink = linkTo(
					methodOn(EvidenceController.class).getEvidenceHierarchyDetails(evidenceSubmissionId, request))
							.withRel(Constants.TRANSFER);
			linkList.add(transferBatchLink);
		}

		Link selfLink = linkTo(
				methodOn(EvidenceController.class).getEvidenceHierarchyDetails(evidenceSubmissionId, request))
						.withSelfRel();
		linkList.add(selfLink);

		Resources<Evidence> evidenceResources = new Resources<>(evidenceList, linkList);
		logger.info("No of evidence Returned " + results);
		return new ResponseEntity<Resources<Evidence>>(evidenceResources, HttpStatus.OK);

	}

}
