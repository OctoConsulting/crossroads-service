package gov.fbi.elabs.crossroads.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.Batch;
import gov.fbi.elabs.crossroads.domain.BatchDetails;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.BatchRepository;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Service
@Transactional
public class BatchService {

	@Autowired
	private BatchRepository batchRepo;

	private static final Logger logger = LoggerFactory.getLogger(BatchService.class);

	public BatchDetails getBatchDetails(int employeeId, int days, String query, String orderBy, String sortBy,
			int pageNum, int limit) throws BaseApplicationException {

		if (StringUtils.isNotEmpty(query)) {
			query = query + "%";
		}

		if (Constants.EXPIRES.equalsIgnoreCase(orderBy)) {
			orderBy = Constants.EXPIRES;
		} else {
			orderBy = Constants.NAME;
		}

		if (Constants.DESC.equalsIgnoreCase(sortBy)) {
			sortBy = Constants.DESC;
		} else {
			sortBy = Constants.ASC;
		}

		int offset = (pageNum - 1) * limit;

		BatchDetails details = new BatchDetails();
		List<Batch> batchList = batchRepo.getBatchDetails(employeeId, days, query, orderBy, sortBy, offset, limit);
		int totalCount = batchRepo.getBatchDetailsCount(employeeId, days, query);
		details.setBatchList(batchList);
		details.setTotalCounts(totalCount);

		int results = batchList != null ? batchList.size() : 0;

		logger.info("No of Batches returned " + results);
		return details;
	}

}
