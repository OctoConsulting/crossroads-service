package gov.fbi.elabs.crossroads.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = CRSController.BasePath+"/v1/location")
@Api(tags = "Location", description = "Location Operations")
public class LocationController {

	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
	
//	@Autowired
//	private LocationService locationService;
	
	@RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Fetch all workflow")
	public ResponseEntity<String> test() throws BaseApplicationException{
	return new ResponseEntity<String>("Works",HttpStatus.OK);
}
	
	
	
}
