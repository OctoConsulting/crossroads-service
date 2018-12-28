package gov.fbi.elabs.crossroads.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.EmployeeOrganizationLocation;
import gov.fbi.elabs.crossroads.domain.Location;
import gov.fbi.elabs.crossroads.domain.Organization;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.EmployeeOrganizationLocationRepository;
import gov.fbi.elabs.crossroads.repository.LocationRepository;
import gov.fbi.elabs.crossroads.repository.OrganizationRepository;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Service
@Transactional
public class LocationService {

	private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private EmployeeOrganizationLocationRepository employeeOrgLocRepository;

	public List<Location> getAllLocations() throws BaseApplicationException {
		return locationRepository.getAllLocations();
	}

	public List<Location> getLabInformation(Integer employeeId, String status) throws BaseApplicationException {

		if (Constants.ACTIVE.equalsIgnoreCase(status)) {
			status = Constants.ACTIVE;
		} else if (Constants.INACTIVE.equalsIgnoreCase(status)) {
			status = Constants.INACTIVE;
		} else {
			status = Constants.EVERYTHING;
		}

		List<Location> locList = locationRepository.getLabInformation(employeeId, status);
		Map<Integer, EmployeeOrganizationLocation> locMap = this.getOrgLocMap(employeeId, status);
		for (Location loc : locList) {
			if (locMap.containsKey(loc.getLocationId())) {
				loc.setIsDefault(true);
			}
		}

		int res = locList != null ? locList.size() : 0;
		logger.info("No of locations returned " + res);
		return locList;

	}

	public List<Organization> getUnitInformation(Integer employeeId, Integer locationId, String status)
			throws BaseApplicationException {

		if (Constants.ACTIVE.equalsIgnoreCase(status)) {
			status = Constants.ACTIVE;
		} else if (Constants.INACTIVE.equalsIgnoreCase(status)) {
			status = Constants.INACTIVE;
		} else {
			status = Constants.EVERYTHING;
		}

		List<Organization> orgList = organizationRepository.getAtUnitDetails(locationId, employeeId, status);
		int res = orgList != null ? orgList.size() : 0;
		logger.info("No of orgs " + res);
		return orgList;
	}

	public Map<Integer, EmployeeOrganizationLocation> getOrgLocMap(Integer employeeId, String status) {
		List<EmployeeOrganizationLocation> orgLocList = employeeOrgLocRepository.getEmployeeOrgLocDetails(employeeId,
				status);
		Map<Integer, EmployeeOrganizationLocation> orgLocMap = new HashMap<>();

		for (EmployeeOrganizationLocation loc : orgLocList) {
			orgLocMap.put(loc.getLocationId(), loc);
		}
		return orgLocMap;
	}

}
