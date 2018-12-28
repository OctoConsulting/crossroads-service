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

import gov.fbi.elabs.crossroads.domain.CustodyArea;
import gov.fbi.elabs.crossroads.domain.CustodyLocation;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.service.CustodyAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath + "/v1/custody", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Custody", description = "Custody Information")
public class CustodyController {

	@Autowired
	private CustodyAreaService custodyAreaService;

	private static final Logger logger = LoggerFactory.getLogger(CustodyArea.class);

	@RequestMapping(value = "/area", method = RequestMethod.GET)
	@ApiOperation(value = "Fetch Custody Area list for the employeeID, locationID and organizationID providedd")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "employeeId", value = "Provide employeeId performing the transfer", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "atLabId", value = "Provide AtLabId selected for the employee", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "atUnitId", value = "Provide AtUnitId selected for the employee", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "status", value = "Provide status of the Transfer Type", dataType = "string", paramType = "query", allowableValues = "Everything,Active,Inactive", defaultValue = "Everything") })
	public ResponseEntity<Resources<CustodyArea>> getCustodyAreaInfo(
			@RequestParam(value = "employeeId", required = true) Integer employeeId,
			@RequestParam(value = "atLabId", required = true) Integer locationId,
			@RequestParam(value = "atUnitId", required = true) Integer organizationId,
			@RequestParam(value = "status", required = true, defaultValue = "Everything") String status)
			throws BaseApplicationException {

		List<CustodyArea> areaList = custodyAreaService.getCustodyAreaList(employeeId, locationId, organizationId,
				status);
		int res = areaList != null ? areaList.size() : 0;
		logger.info("No of Custody Area returned " + res);

		for (CustodyArea area : areaList) {
			area.add(linkTo(methodOn(CustodyController.class).getCustodyAreaInfo(employeeId, locationId, organizationId,
					status)).withSelfRel());
		}

		Link selfLink = linkTo(
				methodOn(CustodyController.class).getCustodyAreaInfo(employeeId, locationId, organizationId, status))
						.withSelfRel();
		Resources<CustodyArea> resource = new Resources<>(areaList, selfLink);
		return new ResponseEntity<Resources<CustodyArea>>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "/location", method = RequestMethod.GET)
	@ApiOperation(value = "Fetch Custody Area list for the employeeID, locationID and organizationID providedd")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "custodyAreaId", value = "Provide custodyAreaId selected", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "status", value = "Provide status of the Transfer Type", dataType = "string", paramType = "query", allowableValues = "Everything,Active,Inactive", defaultValue = "Everything") })
	public ResponseEntity<Resources<CustodyLocation>> getCustodyLocationList(
			@RequestParam(value = "custodyAreaId", required = true) Integer custodyAreaId,
			@RequestParam(value = "status", required = true, defaultValue = "Everything") String status)
			throws BaseApplicationException {

		List<CustodyLocation> locList = custodyAreaService.getCustodyLocationList(custodyAreaId, status);
		int res = locList != null ? locList.size() : 0;
		logger.info("No of locs returned " + res);

		for (CustodyLocation loc : locList) {
			loc.add(linkTo(methodOn(CustodyController.class).getCustodyLocationList(custodyAreaId, status))
					.withSelfRel());
		}

		Link selfLink = linkTo(methodOn(CustodyController.class).getCustodyLocationList(custodyAreaId, status))
				.withSelfRel();
		Resources<CustodyLocation> resource = new Resources<>(locList, selfLink);
		return new ResponseEntity<Resources<CustodyLocation>>(resource, HttpStatus.OK);
	}

}
