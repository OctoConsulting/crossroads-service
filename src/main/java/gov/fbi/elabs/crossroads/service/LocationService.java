package gov.fbi.elabs.crossroads.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.Location;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.LocationRepository;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Service
@Transactional
public class LocationService {

	private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

	@Autowired
	private LocationRepository locationRepository;

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
		int res = locList != null ? locList.size() : 0;
		logger.info("No of locations returned " + res);
		return locList;

	}

}
