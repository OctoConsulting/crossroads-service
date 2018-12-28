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

import gov.fbi.elabs.crossroads.domain.EvidenceTransferType;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Repository
@SuppressWarnings("unchecked")
public class TransferTypeRepository extends BaseRepository<EvidenceTransferType> {

	private static final Logger logger = LoggerFactory.getLogger(TransferTypeRepository.class);

	public List<EvidenceTransferType> getTransferType(Set<String> codeSet, String status)
			throws BaseApplicationException {
		Criteria cr = getCurrentSession().createCriteria(EvidenceTransferType.class);

		if (CollectionUtils.isNotEmpty(codeSet)) {
			cr.add(Restrictions.in("transferTypeCode", codeSet));
		}

		if (StringUtils.isNotEmpty(status) && Constants.ACTIVE.equalsIgnoreCase(status)) {
			cr.add(Restrictions.eq("isActive", true));
		} else if (StringUtils.isNotEmpty(status) && Constants.INACTIVE.equalsIgnoreCase(status)) {
			cr.add(Restrictions.eq("isActive", false));
		}
		List<EvidenceTransferType> typeList = cr.list();
		int results = typeList != null ? typeList.size() : 0;
		logger.info("No. of Types " + results);
		return typeList;

	}

}
