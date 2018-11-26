package gov.fbi.elabs.crossroads.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.Employee;
import gov.fbi.elabs.crossroads.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	public List<Employee> getEmployees(String employeeIds, String emailIds){
		
		Set<Integer> employeeSet = new HashSet<>();
		
		if(StringUtils.isNotEmpty(employeeIds)){
			String[] eIds = employeeIds.split(",");
			
			for(String eId : eIds){
				if(StringUtils.isNumeric(eId)){
					employeeSet.add(Integer.parseInt(eId));
				}
			}
		}
		
		Set<String> emailIdSet = new HashSet<>();
		
		if(StringUtils.isNotEmpty(emailIds)){
			String[] ids = emailIds.split(",");
			
			for(String id : ids){
				emailIdSet.add(id);
			}
		}
		
		List<Employee> employeeList = employeeRepository.getEmployees(employeeSet, emailIdSet);
		int results = employeeList != null ? employeeList.size() : 0;
		logger.info("No. of employee returned " + results);
		return employeeList;
	}

}
