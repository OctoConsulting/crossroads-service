package gov.fbi.elabs.crossroads.service;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.BatchTransferTracker;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.BatchTransferTrackerRepository;

@Service
@Transactional
public class BatchTransferTrackerService {

	private static final Logger logger = LoggerFactory.getLogger(BatchTransferTrackerService.class);

	@Autowired
	private BatchTransferTrackerRepository batchTransferTrackerRepository;

	public BatchTransferTracker createTracker(BatchTransferTracker tracker) throws BaseApplicationException {
		BatchTransferTracker track = batchTransferTrackerRepository.createBatchTransferTracker(tracker);
		logger.info("Tracker created " + track.getId());
		return track;
	}

	public BatchTransferTracker updateEndTime(BatchTransferTracker tracker) throws BaseApplicationException {
		tracker.setEndTime(new Timestamp(System.currentTimeMillis()));
		tracker.setIsActive(false);
		BatchTransferTracker track = batchTransferTrackerRepository.updateBatchTransferTracker(tracker);
		logger.info("Tracker updatedd " + track.getId());
		return track;
	}

	public int getTrackerPerEmployeeId(int employeeId) throws BaseApplicationException {
		int count = batchTransferTrackerRepository.getTrackPerEmployeeId(employeeId);
		logger.info("The no of batches in progress " + count);
		return count;
	}

}
