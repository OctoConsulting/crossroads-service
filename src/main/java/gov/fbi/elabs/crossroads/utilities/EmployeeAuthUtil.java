package gov.fbi.elabs.crossroads.utilities;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import gov.fbi.elabs.crossroads.domain.Employee;
import gov.fbi.elabs.crossroads.domain.EmployeeAuth;
import gov.fbi.elabs.crossroads.service.AuthService;
import gov.fbi.elabs.crossroads.service.EmployeeService;

@Service
public class EmployeeAuthUtil {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeAuthUtil.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AuthService authService;

	public EmployeeAuth getEmployeeAuthDetails(String username) {

		EmployeeAuth auth = new EmployeeAuth();
		Employee emp = employeeService.getEmployeeDetails(username);

		if (emp != null && emp.getEmployeeID() != null) {
			auth.setDisplayName(emp.getDisplayName());
			auth.setEmployeeId(emp.getEmployeeID());
			System.out.println("Emp :" + emp.getUserName());
			auth.setUserName(emp.getUserName());

			List<String> roleList = authService.getRoleList(emp.getEmployeeID());
			auth.setRoleList(roleList);

			List<String> taskList = authService.getTaskList(emp.getEmployeeID());
			auth.setTaskList(taskList);
			logger.info("Employee Id " + emp.getEmployeeID());
		}

		return auth;
	}

	public Boolean checkTaskPerm(HttpServletRequest request, String task) {

		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		EmployeeAuth employeeAuth = this.getEmployeeAuthDetails(username);

		if (employeeAuth.getEmployeeId() == null
				|| !CollectionUtils.containsAny(employeeAuth.getRoleList(), Constants.ROLES)
				|| !employeeAuth.getTaskList().contains(task)) {
			return false;
		}

		return true;
	}

	public Boolean checkRoleTasks(HttpServletRequest request) {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		EmployeeAuth employeeAuth = this.getEmployeeAuthDetails(username);

		if (employeeAuth.getEmployeeId() == null
				|| !CollectionUtils.containsAny(employeeAuth.getRoleList(), Constants.ROLES)
				|| !(employeeAuth.getTaskList().containsAll(Constants.TASKS))) {
			return false;
		}
		return true;
	}

}
