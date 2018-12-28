package gov.fbi.elabs.crossroads.repository;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.Employee;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Repository
@SuppressWarnings("unchecked")
public class EmployeeRepository extends BaseRepository<Employee> {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeRepository.class);

	public List<Employee> getEmployees(Set<Integer> employeeIds, Set<Integer> exceptSet, Set<String> emailIds,
			String status) {
		Criteria cr = getCurrentSession().createCriteria(Employee.class);

		if (CollectionUtils.isNotEmpty(employeeIds)) {
			cr.add(Restrictions.in("employeeID", employeeIds));
		}

		if (CollectionUtils.isNotEmpty(emailIds)) {
			cr.add(Restrictions.in("email", emailIds));
		}

		if (CollectionUtils.isNotEmpty(exceptSet)) {
			cr.add(Restrictions.not(Restrictions.in("employeeID", exceptSet)));
		}

		if (StringUtils.isNotEmpty(status) && Constants.ACTIVE.equalsIgnoreCase(status)) {
			cr.add(Restrictions.eq("isActive", true));
		} else if (StringUtils.isNotEmpty(status) && Constants.INACTIVE.equalsIgnoreCase(status)) {
			cr.add(Restrictions.eq("isActive", false));
		}

		List<Employee> employeeList = cr.list();
		int results = employeeList != null ? employeeList.size() : 0;
		logger.info("No of Employee returned " + results);
		return employeeList;
	}

}
