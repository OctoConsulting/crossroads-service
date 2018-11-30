package gov.fbi.elabs.crossroads.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.Evidence;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.EvidenceRepository;

@Service
@Transactional
public class EvidenceService {

	@Autowired
	private EvidenceRepository evidenceRepository;

	private static final Logger logger = LoggerFactory.getLogger(EvidenceService.class);

	public List<Evidence> getEvidenceListForBatch(Integer batchId) throws BaseApplicationException {
		List<Evidence> evidenceList = evidenceRepository.getEvidenceList(batchId);

		for (Evidence evidence : evidenceList) {
			createHierarchy(evidence);
		}

		int results = evidenceList != null ? evidenceList.size() : 0;
		logger.info("Evidences returned " + results);
		return evidenceList;
	}

	public void createHierarchy(Evidence evidence) throws BaseApplicationException {

		Integer evidenceSubmissionId = evidence.getEvidenceSubmissionId();
		if (evidenceSubmissionId == null) {
			return;
		}

		List<Evidence> evidenceList = evidenceRepository.getEvidenceHierarchy(evidenceSubmissionId);
		evidence.setHierarchy(evidenceList);
		if (CollectionUtils.isEmpty(evidenceList)) {
			return;
		}

		for (Evidence ev : evidenceList) {
			createHierarchy(ev);
		}
	}

}
