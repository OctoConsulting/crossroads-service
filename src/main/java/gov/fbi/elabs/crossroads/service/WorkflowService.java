//package gov.fbi.elabs.crossroads.service;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import gov.fbi.elabs.crossroads.domain.Workflow;
//import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
//import gov.fbi.elabs.crossroads.repository.WorkflowRepository;
//
//@Service
//@Transactional
//public class WorkflowService {
//	
//	@Autowired
//	private WorkflowRepository workflowRepository;
//	
//	private static final Logger logger = LoggerFactory.getLogger(WorkflowService.class);
//	
//	public List<Workflow> getAllWorkflow() throws BaseApplicationException{
//		return workflowRepository.getAllWorkFlows();
//	}
//}
