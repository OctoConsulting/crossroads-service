//package gov.fbi.elabs.crossroads.controller;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import gov.fbi.elabs.crossroads.domain.Workflow;
//import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
//import gov.fbi.elabs.crossroads.service.WorkflowService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping(value = CRSController.BasePath+"/v1/workflow", produces = MediaType.APPLICATION_JSON_VALUE)
//@Api(tags = "Workflow", description = "Workflow Operations")
//public class WorkflowController {
//
//	private static final Logger logger = LoggerFactory.getLogger(WorkflowController.class);
//	
//	@Autowired
//	private WorkflowService workflowService;
//	
//	@RequestMapping(method = RequestMethod.GET)
//    @ApiOperation(value = "Fetch all workflow")
//	public List<Workflow> getAllWorkflow() throws BaseApplicationException{
//		return workflowService.getAllWorkflow();
//	}
//}
