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

import gov.fbi.elabs.crossroads.domain.EvidenceTransferReason;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Repository
@SuppressWarnings("unchecked")
public class TransferReasonRepository extends BaseRepository<EvidenceTransferReason> {

	private static final Logger logger = LoggerFactory.getLogger(TransferReasonRepository.class);

	public List<EvidenceTransferReason> getTransferReason(Set<Integer> idSet, String status)
			throws BaseApplicationException {
		logger.info("Get Transfer Reasons ");
		Criteria cr = getCurrentSession().createCriteria(EvidenceTransferReason.class);

		if (CollectionUtils.isNotEmpty(idSet)) {
			cr.add(Restrictions.in("transferReasonId", idSet));
		}

		if (StringUtils.isNotEmpty(status) && Constants.ACTIVE.equalsIgnoreCase(status)) {
			cr.add(Restrictions.eq("isActive", true));
		} else if (StringUtils.isNotEmpty(status) && Constants.INACTIVE.equalsIgnoreCase(status)) {
			cr.add(Restrictions.eq("isActive", false));
		}

		List<EvidenceTransferReason> reasonList = cr.list();
		int results = reasonList != null ? reasonList.size() : 0;
		logger.info("No. of Reasons " + results);
		return reasonList;
	}

}
