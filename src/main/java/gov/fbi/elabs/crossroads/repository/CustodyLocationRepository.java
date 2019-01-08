package gov.fbi.elabs.crossroads.repository;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.CustodyLocation;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Repository
@SuppressWarnings("unchecked")
public class CustodyLocationRepository extends BaseRepository<CustodyLocationRepository> {

	private static final Logger logger = LoggerFactory.getLogger(CustodyLocationRepository.class);

	public List<CustodyLocation> getCustodyLocationList(int areaId, String status) throws BaseApplicationException {
		Criteria cr = getCurrentSession().createCriteria(CustodyLocation.class);
		cr.add(Restrictions.eq("storageAreaId", areaId));

		if (StringUtils.isNotEmpty(status) && Constants.ACTIVE.equalsIgnoreCase(status)) {
			cr.add(Restrictions.eq("isActive", true));
		} else if (StringUtils.isNotEmpty(status) && Constants.INACTIVE.equalsIgnoreCase(status)) {
			cr.add(Restrictions.eq("isActive", false));
		}

		List<CustodyLocation> locList = cr.list();
		int res = locList != null ? locList.size() : 0;
		logger.info("No of results returned " + res);
		return locList;
	}

}
