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

@Service
@Transactional
public class LocationService {

	private static final Logger logger = LoggerFactory.getLogger(LocationService.class);
	
	@Autowired
	private LocationRepository locationRepository;
	
	public List<Location> getAllLocations() throws BaseApplicationException{
		return locationRepository.getAllLocations();
	}
	
}
