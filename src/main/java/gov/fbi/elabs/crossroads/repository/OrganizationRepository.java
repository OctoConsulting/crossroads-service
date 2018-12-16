package gov.fbi.elabs.crossroads.repository;

import java.util.List;

import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.Organization;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;

@Repository
@SuppressWarnings("unchecked")
public class OrganizationRepository extends BaseRepository<Organization> {

	private static Logger logger = LoggerFactory.getLogger(OrganizationRepository.class);

	public List<Organization> getAtUnitInfo(Integer locationId, Integer employeeId) throws BaseApplicationException {
		StringBuilder builder = new StringBuilder();
		builder.append("select * from Organization where OrganizationID in (");
		builder.append(" select el.OrganizationID from EmployeeOrganizationLocation el, OrganizationLocation ol");
		builder.append(" where el.OrganizationID = ol.OrganizationID");
		builder.append(" and el.LocationID =  " + locationId + " and EmployeeID =" + employeeId + ")");

		SQLQuery sqlQuery = createSQLQuery(builder.toString());
		sqlQuery.addEntity(Organization.class);
		List<Organization> orgs = sqlQuery.list();
		int results = orgs != null ? orgs.size() : 0;

		logger.info("Total no of orgs returned: " + results);
		return orgs;
	}

}
