package gov.fbi.elabs.crossroads.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.Workflow;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;

@Repository
@SuppressWarnings("unchecked")
public class WorkflowRepository extends BaseRepository<Workflow>{
	
	private static final Logger logger = LoggerFactory.getLogger(WorkflowRepository.class);
	
	public List<Workflow> getAllWorkFlows() throws BaseApplicationException{
		Criteria cr = getCurrentSession().createCriteria(Workflow.class);
		List<Workflow> workflowList = cr.list();
		int size = workflowList == null ? 0 : workflowList.size();
		logger.info("Results returned " + size);
		return workflowList;
	}
	
}
