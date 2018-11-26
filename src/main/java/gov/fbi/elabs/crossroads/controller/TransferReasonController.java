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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.fbi.elabs.crossroads.domain.EvidenceTransferReason;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.service.TransferReasonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath + "/v1/transferReason")
@Api(tags = "Transfer", description = "Transfer Operations")
public class TransferReasonController {

	@Autowired
	private TransferReasonService transferReasonService;

	private static final Logger logger = LoggerFactory.getLogger(TransferReasonController.class);

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Fetch transfer Reason")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", value = "Provide Transfer Reason ids", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "Provide status of the Transfer Reason", dataType = "string", paramType = "query", allowableValues = "Everything,Active,Inactive", defaultValue = "Everything") })
	public ResponseEntity<Resources<EvidenceTransferReason>> getTransferReason(
			@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "status", required = true, defaultValue = "Everything") String status)
			throws BaseApplicationException {
		List<EvidenceTransferReason> transferList = transferReasonService.getTransferReason(ids, status);

		for (EvidenceTransferReason reason : transferList) {
			reason.add(linkTo(methodOn(TransferReasonController.class)
					.getTransferReason(reason.getTransferReasonId().toString(), status)).withSelfRel().expand());
		}

		Link selflink = linkTo(methodOn(TransferReasonController.class).getTransferReason(ids, status)).withSelfRel()
				.expand();
		Resources<EvidenceTransferReason> reasonResources = new Resources<>(transferList, selflink);

		int results = transferList != null ? transferList.size() : 0;
		logger.info("No. of Transfer reasons " + results);
		return new ResponseEntity<Resources<EvidenceTransferReason>>(reasonResources, HttpStatus.OK);
	}

}
