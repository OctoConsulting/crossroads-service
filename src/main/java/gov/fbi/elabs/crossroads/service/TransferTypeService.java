package gov.fbi.elabs.crossroads.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.EvidenceTransferType;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.TransferTypeRepository;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Service
@Transactional
public class TransferTypeService {
	
	@Autowired
	private TransferTypeRepository transferTypeRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(TransferTypeService.class);
	
	public List<EvidenceTransferType> getTransferType(String codes,String status) throws BaseApplicationException{
		
		Set<String> codeSet = new HashSet<>();
		if(StringUtils.isNotEmpty(codes)){
			String[] codeArr = codes.split(",");
			for(String code : codeArr){
				codeSet.add(code);
			}
		}
		
		if(Constants.ACTIVE.equalsIgnoreCase(status)){
			status = Constants.ACTIVE;
		}else if(Constants.INACTIVE.equalsIgnoreCase(status)){
			status = Constants.INACTIVE;
		}else{
			status = Constants.EVERYTHING;
		}
		
		List<EvidenceTransferType> typeList = transferTypeRepo.getTransferType(codeSet, status);
		int results = typeList != null ? typeList.size() : 0;
		logger.info("No. of types " + results);
		return typeList;
		
	}
	
	
}
