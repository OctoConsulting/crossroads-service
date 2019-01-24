package gov.fbi.elabs.crossroads.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.CustodyArea;
import gov.fbi.elabs.crossroads.domain.CustodyLocation;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.CustodyAreaRepository;
import gov.fbi.elabs.crossroads.repository.CustodyLocationRepository;

@Service
@Transactional
public class CustodyAreaService {

	@Autowired
	private CustodyAreaRepository custodyAreaRepository;

	@Autowired
	private CustodyLocationRepository custodyLocationRepository;

	private static final Logger logger = LoggerFactory.getLogger(CustodyAreaService.class);

	public List<CustodyArea> getCustodyAreaList(Integer employeeId, Integer locationId, Integer organizationId,
			String status) throws BaseApplicationException {

		List<CustodyArea> areaList = custodyAreaRepository.getCustodyAreaInfo(employeeId, locationId, organizationId,
				status);
		int res = areaList != null ? areaList.size() : 0;
		logger.info("No of areas returned " + res);
		return areaList;
	}

	public List<CustodyLocation> getCustodyLocationList(Integer areaId, String status) throws BaseApplicationException {
		List<CustodyLocation> locList = custodyLocationRepository.getCustodyLocationList(areaId, status);

		int res = locList != null ? locList.size() : 0;
		logger.info("No of locs returneed " + res);
		return locList;
	}

	public Boolean validateTransferAuth(Integer employeeId, Integer custodyAreaId, String type)
			throws BaseApplicationException {

		Boolean auth = custodyAreaRepository.custodyAreaAuthorization(employeeId, custodyAreaId, type);
		return auth;

	}

}
