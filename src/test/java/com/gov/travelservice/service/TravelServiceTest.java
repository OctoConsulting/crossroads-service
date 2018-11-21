package com.gov.travelservice.service;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest({ "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect", "logging.level.org.hibernate.SQL=DEBUG",
		"logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class TravelServiceTest {

	@Autowired
	TravelService travelService;
	
	@Ignore
	public void testGetTravelRecordById() {
		System.out.println(travelService.getTravelServiceById(1).getRequester().getFirst_name());
	}

	@Ignore
	public void testGetTravelServiceList() {
		System.out.println(travelService.getTravelServiceList(1,0,10).size());
	}

	@Ignore
	public void testGetTravelServiceList1() {
		System.out.println("");
	}

}
