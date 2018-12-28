package gov.fbi.elabs.crossroads.repository;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.Location;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Repository
@SuppressWarnings("unchecked")
public class LocationRepository extends BaseRepository<Location> {

	private static final Logger logger = LoggerFactory.getLogger(LocationRepository.class);

	public List<Location> getAllLocations() throws BaseApplicationException {
		Criteria cr = getCurrentSession().createCriteria(Location.class);
		List<Location> locationList = cr.list();
		return locationList;
	}

	public List<Location> getLabInformation(Integer employeeId, String status) throws BaseApplicationException {

		StringBuilder builder = new StringBuilder();
		builder.append("select distinct l.* from EmployeeOrganizationLocation el, [Location] l");
		builder.append(" where el.LocationID = l.LocationID");
		builder.append(" and EmployeeID = " + employeeId);

		if (StringUtils.isNotEmpty(status) && Constants.ACTIVE.equalsIgnoreCase(status)) {
			builder.append(" and l.IsActive = 1");
		} else if (StringUtils.isNotEmpty(status) && Constants.INACTIVE.equalsIgnoreCase(status)) {
			builder.append(" and l.IsActive = 0");
		}

		SQLQuery sqlQuery = createSQLQuery(builder.toString());
		sqlQuery.addEntity(Location.class);
		List<Location> locList = sqlQuery.list();
		int results = locList != null ? locList.size() : 0;
		logger.info("No of locations returned " + results);
		return locList;
	}

}
