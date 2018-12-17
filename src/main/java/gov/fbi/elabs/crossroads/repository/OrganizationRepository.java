package gov.fbi.elabs.crossroads.repository;

import java.util.List;

import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.Organization;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Repository
@SuppressWarnings("unchecked")
public class OrganizationRepository extends BaseRepository<Organization> {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationRepository.class);

	public List<Organization> getAtUnitDetails(int locationId, int employeeId, String status)
			throws BaseApplicationException {

		StringBuilder builder = new StringBuilder();
		builder.append("select * from Organization where OrganizationID in (");
		builder.append(" select el.OrganizationID from EmployeeOrganizationLocation el, OrganizationLocation ol");
		builder.append(" where el.OrganizationID = ol.OrganizationID");
		builder.append(" and el.LocationID = " + locationId + " and EmployeeID = " + employeeId + " )");

		if (Constants.ACTIVE.equalsIgnoreCase(status)) {
			builder.append(" and IsActive = 1");
		} else if (Constants.INACTIVE.equalsIgnoreCase(status)) {
			builder.append(" and IsActive = 0");
		}

		SQLQuery sqlQuery = createSQLQuery(builder.toString());
		sqlQuery.addEntity(Organization.class);

		List<Organization> orgsList = sqlQuery.list();
		int res = orgsList != null ? orgsList.size() : 0;
		logger.info("No of orgs " + res);
		return orgsList;
	}

}
