package gov.fbi.elabs.crossroads.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.fbi.elabs.crossroads.domain.EmployeeAuth;
import gov.fbi.elabs.crossroads.domain.ErrorMessage;
import gov.fbi.elabs.crossroads.domain.EvidenceTransferUI;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.EvidenceRepository;
import gov.fbi.elabs.crossroads.repository.EvidenceTransferRepository;
import gov.fbi.elabs.crossroads.service.EvidenceTransferService;
import gov.fbi.elabs.crossroads.utilities.Constants;
import gov.fbi.elabs.crossroads.utilities.EmployeeAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Evidence Transfer", description = "Evidence Transfer")
public class EvidenceTransferController {
	private static final Logger logger = LoggerFactory.getLogger(EvidenceTransferController.class);

	@Autowired
	EvidenceTransferService evidenceTransferService;

	@Autowired
	EvidenceTransferRepository evidenceTransferRepository;

	@Autowired
	EvidenceRepository evidenceRepository;

	@Autowired
	EmployeeAuthUtil employeeAuthUtil;

	@RequestMapping(value = "/v1/evidencetransfer/", method = RequestMethod.POST)
	@ApiOperation(value = "Transfer evidence within a batch.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "x-auth-token", value = "x-auth-token", dataType = "string", paramType = "header", required = true) })
	public ResponseEntity evidenceTransfer(@RequestBody EvidenceTransferUI evidenceTransferUI,
			HttpServletRequest request) throws BaseApplicationException {

		logger.info("Manage POST /transferevidence/");

		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		EmployeeAuth employeeAuth = employeeAuthUtil.getEmployeeAuthDetails(username);

		if (employeeAuth.getEmployeeId() == null
				|| !CollectionUtils.containsAny(employeeAuth.getRoleList(), Constants.ROLES)
				|| !employeeAuth.getTaskList().contains(Constants.CAN_VIEW_BATCH)) {

			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		logger.trace("Manage POST /transferevidence/validate");
		if (evidenceTransferUI == null) {
			return new ResponseEntity<List<ErrorMessage>>(HttpStatus.BAD_REQUEST);
		}
		if (evidenceTransferUI.getBatchID() != null) {
			List<ErrorMessage> errorMessageList = evidenceTransferService
					.validateEvidenceTransfer(evidenceTransferUI.getBatchID(), evidenceTransferUI);
			if (!errorMessageList.isEmpty()) {
				return new ResponseEntity<List<ErrorMessage>>(errorMessageList, HttpStatus.BAD_REQUEST);
			}
		} else {
			logger.info("Error as BatchId is missing ");
			return new ResponseEntity<List<ErrorMessage>>(HttpStatus.BAD_REQUEST);
		}

		String userNameForDB = employeeAuth.getUserName();
		final long startTime = System.currentTimeMillis();
		evidenceTransferService.transferEvidence(employeeAuth, evidenceTransferUI);
		final long endTime = System.currentTimeMillis();
		long timeInMillis = endTime - startTime;
		String execTime = "Total execution time for search request: " + (endTime - startTime) + " millis";
		return new ResponseEntity(execTime, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/evidencetransfer/validate", method = RequestMethod.POST)
	@ApiOperation(value = "Validate transfer of a batch with evidence.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-Auth-Token", value = "Authentication Token", paramType = "header", dataType = "string", required = true) })
	public ResponseEntity<List<ErrorMessage>> validateEvidenceTransfer(
			@RequestParam(value = "batchID", required = true) Integer batchID,
			@RequestBody EvidenceTransferUI evidenceTransferUI, HttpServletRequest request)
			throws BaseApplicationException {
		logger.trace("Manage POST /transferevidence/validate");
		if (evidenceTransferUI == null) {
			return new ResponseEntity<List<ErrorMessage>>(HttpStatus.BAD_REQUEST);
		}

		List<ErrorMessage> errorMessageList = evidenceTransferService.validateEvidenceTransfer(batchID,
				evidenceTransferUI);
		if (errorMessageList.isEmpty()) {
			return new ResponseEntity<List<ErrorMessage>>(HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ErrorMessage>>(errorMessageList, HttpStatus.BAD_REQUEST);
		}

	}

}