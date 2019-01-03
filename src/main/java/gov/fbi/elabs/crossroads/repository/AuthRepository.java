package gov.fbi.elabs.crossroads.repository;

import java.util.List;

import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class AuthRepository extends BaseRepository<String> {

	private static final Logger logger = LoggerFactory.getLogger(AuthRepository.class);

	public List<String> getAuthRole(Integer empId) {
		StringBuilder builder = new StringBuilder();
		builder.append("Select Name from AuthRole");
		builder.append(" where Id in");
		builder.append(" (select RoleId from EmployeeAuthRole where EmployeeId = " + empId + ")");

		SQLQuery sqlQuery = createSQLQuery(builder.toString());
		// sqlQuery.addEntity(String.class);
		List<String> authRoleList = sqlQuery.list();
		logger.info("No of roles returned " + authRoleList.size());
		return authRoleList;
	}

	public List<String> getAuthTask(Integer empId) {
		StringBuilder builder = new StringBuilder();
		builder.append("Select Name from AuthTask");
		builder.append(" where id in");
		builder.append(" (Select TaskId from AuthRoleTask where RoleId in (");
		builder.append(" select RoleId from EmployeeAuthRole where EmployeeId = " + empId + " ))");

		SQLQuery sqlQuery = createSQLQuery(builder.toString());
		// sqlQuery.addEntity(String.class);
		List<String> authTaskList = sqlQuery.list();
		logger.info("No of tasks returned " + authTaskList.size());
		return authTaskList;
	}

}
