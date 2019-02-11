package gov.fbi.elabs.crossroads.repository;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.BatchTransferTracker;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;

@Repository
@SuppressWarnings("unchecked")
public class BatchTransferTrackerRepository extends BaseRepository<BatchTransferTracker> {

	private final static Logger logger = LoggerFactory.getLogger(BatchTransferTrackerRepository.class);

	public BatchTransferTracker createBatchTransferTracker(BatchTransferTracker tracker)
			throws BaseApplicationException {

		Session session = openSession();
		session.beginTransaction();
		Integer seqId = (Integer) session.save(tracker);
		BatchTransferTracker track = session.get(BatchTransferTracker.class, seqId);
		session.getTransaction().commit();
		session.close();
		logger.info("BatchTracker added " + track.getId());
		return track;
	}

	public BatchTransferTracker updateBatchTransferTracker(BatchTransferTracker tracker)
			throws BaseApplicationException {
		BatchTransferTracker update = update(tracker, false);
		logger.info("Tracker updated " + update.getId());
		return update;
	}

	public int getTrackPerEmployeeId(int employeeId) throws BaseApplicationException {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"Select count(*) from BatchTransferTracker where EndTime is null and EmployeeID = " + employeeId);
		SQLQuery sqlQuery = createSQLQuery(builder.toString());
		int count = (int) sqlQuery.list().get(0);
		return count;
	}
}
