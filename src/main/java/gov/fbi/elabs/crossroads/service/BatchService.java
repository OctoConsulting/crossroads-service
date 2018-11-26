package gov.fbi.elabs.crossroads.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.Batch;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.BatchRepository;

@Service
@Transactional
public class BatchService {

	@Autowired
	private BatchRepository batchRepo;

	private static final Logger logger = LoggerFactory.getLogger(BatchService.class);

	public List<Batch> getBatchDetails() throws BaseApplicationException {
		return batchRepo.getBatchDetails(63718, 1560);
	}

}
