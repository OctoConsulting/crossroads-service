package gov.fbi.elabs.crossroads.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.EvidenceTransferReason;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.TransferReasonRepository;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Service
@Transactional
public class TransferReasonService {
	
	@Autowired
	private TransferReasonRepository transferReasonRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(TransferReasonService.class);
	
	public List<EvidenceTransferReason> getTransferReason(String ids, String status) throws BaseApplicationException{
		
		Set<Integer> idSet = new HashSet<>();
		if(StringUtils.isNotEmpty(ids)){
			String[] tIds = ids.split(",");
			for(String id : tIds){
				if(StringUtils.isNumeric(id)){
					idSet.add(Integer.parseInt(id));
				}
			}
		}
		
		if(Constants.ACTIVE.equalsIgnoreCase(status)){
			status = Constants.ACTIVE;
		}else if(Constants.INACTIVE.equalsIgnoreCase(status)){
			status = Constants.INACTIVE;
		}else{
			status = Constants.EVERYTHING;
		}
		
		List<EvidenceTransferReason> reasonList = transferReasonRepo.getTransferReason(idSet, status);
		int results = reasonList != null ? reasonList.size() : 0;
		logger.info("Reasons List " + results);
		return reasonList;
	}
	

}
