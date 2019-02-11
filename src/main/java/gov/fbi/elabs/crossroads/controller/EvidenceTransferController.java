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
import gov.fbi.elabs.crossroads.service.BatchTransferTrackerService;
import gov.fbi.elabs.crossroads.service.CustodyAreaService;
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
	CustodyAreaService custodyAreaService;

	@Autowired
	EmployeeAuthUtil employeeAuthUtil;

	@Autowired
	BatchTransferTrackerService batchTransferTrackerService;

	@RequestMapping(value = "/v1/evidencetransfer/", method = RequestMethod.POST)
	@ApiOperation(value = "Transfer evidence within a batch.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "x-auth-token", value = "x-auth-token", dataType = "string", paramType = "header", required = true) })
	public ResponseEntity evidenceTransfer(@RequestBody EvidenceTransferUI evidenceTransferUI,
			HttpServletRequest request) throws BaseApplicationException {

		logger.info("Manage POST /transferevidence/");
		final long startTime = System.currentTimeMillis();

		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		EmployeeAuth employeeAuth = employeeAuthUtil.getEmployeeAuthDetails(username);

		if (employeeAuth == null || employeeAuth.getEmployeeId() == null
				|| !CollectionUtils.containsAny(employeeAuth.getRoleList(), Constants.ROLES)
				|| !employeeAuth.getTaskList().contains(Constants.CAN_TRANSFER_BATCH)) {

			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

		if (evidenceTransferUI == null) {
			return new ResponseEntity<List<ErrorMessage>>(HttpStatus.BAD_REQUEST);
		}
		if (evidenceTransferUI.getBatchID() != null) {
			List<ErrorMessage> errorMessageList = evidenceTransferService
					.validateEvidenceTransfer(evidenceTransferUI.getBatchID(), evidenceTransferUI, employeeAuth);
			if (!errorMessageList.isEmpty()) {
				return new ResponseEntity<List<ErrorMessage>>(errorMessageList, HttpStatus.BAD_REQUEST);
			}
		} else {
			logger.info("Error as BatchId is missing ");
			return new ResponseEntity<List<ErrorMessage>>(HttpStatus.BAD_REQUEST);
		}

		String userNameForDB = employeeAuth.getUserName();

		boolean status = evidenceTransferService.transferEvidence(employeeAuth, evidenceTransferUI);

		final long endTime = System.currentTimeMillis();
		long timeInMillis = endTime - startTime;

		String execTime = "Total execution time for transfer: " + (endTime - startTime) + " millis";
		if (!status) {
			execTime = execTime.concat(" Wasnt successful to transfer the transaction");
		}
		ErrorMessage message = new ErrorMessage();
		message.setFieldName("executionTime");
		message.setErrorMessages(execTime);

		if (!status) {
			return new ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity(message, HttpStatus.OK);
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

		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		EmployeeAuth employeeAuth = employeeAuthUtil.getEmployeeAuthDetails(username);

		if (employeeAuth.getEmployeeId() == null
				|| !CollectionUtils.containsAny(employeeAuth.getRoleList(), Constants.ROLES)
				|| !employeeAuth.getTaskList().contains(Constants.CAN_TRANSFER_BATCH)) {

			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

		if (evidenceTransferUI == null) {
			return new ResponseEntity<List<ErrorMessage>>(HttpStatus.BAD_REQUEST);
		}

		List<ErrorMessage> errorMessageList = evidenceTransferService.validateEvidenceTransfer(batchID,
				evidenceTransferUI, employeeAuth);
		if (errorMessageList.isEmpty()) {
			return new ResponseEntity<List<ErrorMessage>>(HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ErrorMessage>>(errorMessageList, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/v1/evidencetransfer/validateTransOut", method = RequestMethod.POST)
	@ApiOperation(value = "Validate transfer of a batch with evidence.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "custodyAreaId", value = "Provide custodyAreaId of evidence ", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "X-Auth-Token", value = "Authentication Token", paramType = "header", dataType = "string", required = true) })
	public ResponseEntity<String> validateTransfer(
			@RequestParam(value = "custodyAreaId", required = true) Integer custodyAreaId)
			throws BaseApplicationException {

		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		EmployeeAuth employeeAuth = employeeAuthUtil.getEmployeeAuthDetails(username);

		if (employeeAuth == null || employeeAuth.getEmployeeId() == null || custodyAreaId == null
				|| !CollectionUtils.containsAny(employeeAuth.getRoleList(), Constants.ROLES)
				|| !employeeAuth.getTaskList().contains(Constants.CAN_TRANSFER_BATCH)) {

			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		boolean auth = custodyAreaService.validateTransferAuth(employeeAuth.getEmployeeId(), custodyAreaId,
				Constants.TRANSFER_OUT);
		if (!auth) {
			ErrorMessage message = new ErrorMessage();
			message.setFieldName("transferOut");
			message.setErrorMessages("Transfer Out is not autherized for this custody area");
			return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
		}

		int count = batchTransferTrackerService.getTrackerPerEmployeeId(employeeAuth.getEmployeeId());
		if (count > 0) {
			ErrorMessage message = new ErrorMessage();
			message.setFieldName("TransferInProgress");
			message.setErrorMessages("Another transaction for this employee is in progress");
			return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
