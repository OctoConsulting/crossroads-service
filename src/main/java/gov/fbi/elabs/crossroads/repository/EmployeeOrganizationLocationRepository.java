package gov.fbi.elabs.crossroads.repository;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.EmployeeOrganizationLocation;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Repository
@SuppressWarnings("unchecked")
public class EmployeeOrganizationLocationRepository extends BaseRepository<EmployeeOrganizationLocation> {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeOrganizationLocationRepository.class);

	public List<EmployeeOrganizationLocation> getEmployeeOrgLocDetails(int employeeId, String status) {

		StringBuilder builder = new StringBuilder();
		builder.append(
				"Select el.EmployeeID as employeeId,el.OrganizationID as organizationId,el.LocationID as locationId,el.IsDefaultSection as isDefault");
		builder.append(" from EmployeeOrganizationLocation el , Location l");
		builder.append(" where el.LocationID = l.LocationID and");
		builder.append(" IsDefaultSection = 1 and el.EmployeeID = " + employeeId);

		if (StringUtils.isNotEmpty(status) && Constants.ACTIVE.equalsIgnoreCase(status)) {
			builder.append(" and el.IsActive = 1");
		} else if (StringUtils.isNotEmpty(status) && Constants.INACTIVE.equalsIgnoreCase(status)) {
			builder.append(" and el.IsActive = 0");
		}

		SQLQuery sqlQuery = createSQLQuery(builder.toString());
		sqlQuery.addScalar("employeeId", new IntegerType());
		sqlQuery.addScalar("organizationId", new IntegerType());
		sqlQuery.addScalar("locationId", new IntegerType());
		sqlQuery.addScalar("isDefault", new BooleanType());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(EmployeeOrganizationLocation.class));

		List<EmployeeOrganizationLocation> list = sqlQuery.list();
		int res = list != null ? list.size() : 0;
		logger.info("No of res returned " + res);
		return list;
	}

}
