package gov.fbi.elabs.crossroads.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

import gov.fbi.elabs.crossroads.domain.EvidenceTransferType;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.service.TransferTypeService;
import gov.fbi.elabs.crossroads.utilities.EmployeeAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath + "/v1/transferType", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Transfer", description = "Transfer Operations")
public class TransferTypeController {

	@Autowired
	private TransferTypeService transferTypeService;

	@Autowired
	private EmployeeAuthUtil employeeAuthUtil;

	private static final Logger logger = LoggerFactory.getLogger(TransferTypeController.class);

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Fetch transfer types")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "codes", value = "Provide Transfer Type codes", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "Provide status of the Transfer Type", dataType = "string", paramType = "query", allowableValues = "Everything,Active,Inactive", defaultValue = "Everything"),
			@ApiImplicitParam(name = "x-auth-token", value = "Authentication Token", paramType = "header", dataType = "string", required = true) })
	public ResponseEntity<Resources<EvidenceTransferType>> getTransferType(
			@RequestParam(value = "codes", required = false) String codes,
			@RequestParam(value = "status", required = true, defaultValue = "Everything") String status,
			HttpServletRequest request) throws BaseApplicationException {

		if (!employeeAuthUtil.checkRoleTasks(request)) {
			return new ResponseEntity<Resources<EvidenceTransferType>>(HttpStatus.UNAUTHORIZED);
		}

		List<EvidenceTransferType> typeList = transferTypeService.getTransferType(codes, status);

		for (EvidenceTransferType type : typeList) {
			type.add(linkTo(
					methodOn(TransferTypeController.class).getTransferType(type.getTransferTypeCode(), status, request))
							.withSelfRel().expand());
		}

		Link selfLink = linkTo(methodOn(TransferTypeController.class).getTransferType(codes, status, request))
				.withSelfRel().expand();
		Resources<EvidenceTransferType> typesResource = new Resources<>(typeList, selfLink);

		int results = typeList != null ? typeList.size() : 0;
		logger.info("No. of Transfer types " + results);

		return new ResponseEntity<Resources<EvidenceTransferType>>(typesResource, HttpStatus.OK);
	}
}
