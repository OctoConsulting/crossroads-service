package gov.fbi.elabs.crossroads.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.fbi.elabs.crossroads.domain.Batch;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.service.BatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath + "/v1/batch")
@Api(tags = "Batch", description = "Batch Operations")
public class BatchController {

	@Autowired
	private BatchService batchService;

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Fetch Batch Details")
	public List<Batch> getBatchDetails() throws BaseApplicationException {
		return batchService.getBatchDetails();
	}

}
