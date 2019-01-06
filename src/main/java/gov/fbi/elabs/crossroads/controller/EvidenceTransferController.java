package gov.fbi.elabs.crossroads.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.fbi.elabs.crossroads.domain.ErrorMessage;
import gov.fbi.elabs.crossroads.domain.EvidenceTransferUI;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.EvidenceRepository;
import gov.fbi.elabs.crossroads.repository.EvidenceTransferRepository;
import gov.fbi.elabs.crossroads.service.EvidenceTransferService;
import gov.fbi.elabs.crossroads.utilities.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "transfer", description = "Evidence Transfer")
public class EvidenceTransferController {
	private static final Logger logger = LoggerFactory.getLogger(EvidenceTransferController.class);

	@Autowired
	EvidenceTransferService evidenceTransferService;

	@Autowired
	EvidenceTransferRepository evidenceTransferRepository;

	@Autowired
	EvidenceRepository evidenceRepository;

	@RequestMapping(value = "/v1/evidencetransfer/", method = RequestMethod.POST)
	@ApiOperation(value = "Transfer evidence within a batch.")
	@ApiImplicitParams({ @ApiImplicitParam(name = Constants.AUTHENTICATION_HEADER) })
	public ResponseEntity<Object> evidenceTransfer(@RequestParam(value = "batchID", required = true) Integer batchID,
			@RequestBody EvidenceTransferUI evidenceTransferUI, HttpServletRequest request) {
		logger.trace("Manage POST /transferevidence/");
		evidenceTransferService.transferEvidence(batchID, evidenceTransferUI);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/evidencetransfer/validate", method = RequestMethod.POST)
	@ApiOperation(value = "Validate transfer of a batch with evidence.")
	// @ApiImplicitParams({ @ApiImplicitParam(name =
	// Constants.AUTHENTICATION_HEADER) })
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
			return new ResponseEntity<List<ErrorMessage>>(errorMessageList, HttpStatus.BAD_GATEWAY);
		}

	}

}
