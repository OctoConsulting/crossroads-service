package gov.fbi.elabs.crossroads.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.Organization;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.OrganizationRepository;

@Service
@Transactional
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

	public List<Organization> getAtUnitInfo(Integer locationId, Integer employeeId) throws BaseApplicationException {
		List<Organization> orgs = organizationRepository.getAtUnitInfo(locationId, employeeId);
		int results = orgs != null ? orgs.size() : 0;
		logger.info("Total no of orgs returned " + results);
		return orgs;
	}

}
