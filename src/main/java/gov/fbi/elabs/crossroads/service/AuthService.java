package gov.fbi.elabs.crossroads.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.repository.AuthRepository;

@Service
@Transactional
public class AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private AuthRepository authRepository;

	public List<String> getRoleList(Integer empId) {
		List<String> roleList = authRepository.getAuthRole(empId);
		logger.info("No of roles returned " + roleList.size());
		return roleList;
	}

	public List<String> getTaskList(Integer empId) {
		List<String> taskList = authRepository.getAuthTask(empId);
		logger.info("No of tasks returned " + taskList.size());
		return taskList;
	}

}
