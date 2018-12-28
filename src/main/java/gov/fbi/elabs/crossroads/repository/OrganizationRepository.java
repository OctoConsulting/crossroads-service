package gov.fbi.elabs.crossroads.repository;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
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
		builder.append(
				"select o.OrganizationID as organizationId,o.OrganizationCode as organizationCode, o.OrganizationName as organizationName,");
		builder.append(
				" o.OrganizationTypeID as organizationTypeId,el.EmployeeID as employeeId ,el.LocationID as locationId,el.IsDefaultSection as isDefault from");
		builder.append(" Organization o, EmployeeOrganizationLocation el, OrganizationLocation ol");
		builder.append(" where el.OrganizationID = o.OrganizationID and");
		builder.append(" el.LocationID = ol.LocationID and el.OrganizationID = ol.OrganizationID");
		builder.append(" and el.LocationID = " + locationId + " and EmployeeID = " + employeeId);

		if (StringUtils.isNotEmpty(status) && Constants.ACTIVE.equalsIgnoreCase(status)) {
			builder.append(" and o.IsActive = 1");
		} else if (StringUtils.isNotEmpty(status) && Constants.INACTIVE.equalsIgnoreCase(status)) {
			builder.append(" and o.IsActive = 0");
		}
		builder.append(" order by IsDefaultSection desc,OrganizationName");

		SQLQuery sqlQuery = createSQLQuery(builder.toString());
		sqlQuery.addScalar("organizationId", new IntegerType());
		sqlQuery.addScalar("organizationCode", new StringType());
		sqlQuery.addScalar("organizationName", new StringType());
		sqlQuery.addScalar("organizationTypeId", new IntegerType());
		sqlQuery.addScalar("employeeId", new IntegerType());
		sqlQuery.addScalar("locationId", new IntegerType());
		sqlQuery.addScalar("isDefault", new BooleanType());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Organization.class));

		List<Organization> orgsList = sqlQuery.list();
		int res = orgsList != null ? orgsList.size() : 0;
		logger.info("No of orgs " + res);
		return orgsList;
	}

}
