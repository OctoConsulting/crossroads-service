package com.gov.travelservice.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gov.travelservice.entity.Notifications;
import com.gov.travelservice.entity.TravelRecord;
import com.gov.travelservice.entity.User;
import com.gov.travelservice.pojo.TravelRecordBO;
import com.gov.travelservice.repository.TravelRecordRepository;
import com.gov.travelservice.repository.UserRepository;

@Service
@Transactional(readOnly=true)
public class TravelService {

	@Autowired
	TravelRecordRepository travelRecordRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	NotificationsService notificationService;

	public Map<String,Map<String, Object>> getTravelServiceList(long userId,int page,int limit) {
		User userreq = new User();
		userreq.setId(userId);
		Map<String,Map<String, Object>> resMap = new HashMap<>();
		Page<TravelRecord> allUserTravelRequests = travelRecordRepository.findByRequester(userreq, PageRequest.of(page, limit));
		addToMap(allUserTravelRequests,resMap,"userAllTravelRequests");
		
		Page<TravelRecord> userPendingTravelRequests = travelRecordRepository.findByRequesterAndStatus(userreq, "Pending", PageRequest.of(page, limit));
		addToMap(userPendingTravelRequests,resMap,"userPendingTravelRequests");
		
		Page<TravelRecord> userUpcomingTravelRequests = travelRecordRepository.findByRequesterAndStatus(userreq, "Approved", PageRequest.of(page, limit));
		addToMap(userUpcomingTravelRequests,resMap,"userUpcomingTravelRequests");
		
		Page<TravelRecord> approverPendingTravelRequests = travelRecordRepository.findByApproverAndStatus(userreq, "Pending", PageRequest.of(page, limit));
		addToMap(approverPendingTravelRequests,resMap,"approverPendingTravelRequests");
		
		Page<TravelRecord> approverApprovedTravelRequests = travelRecordRepository.findByApproverAndStatus(userreq, "Pending", PageRequest.of(page, limit));
		addToMap(approverApprovedTravelRequests,resMap,"approverApprovedTravelRequests");
		
		return resMap;
	}

	private Map<String, Map<String, Object>> addToMap(Page<TravelRecord> travelServicePage,Map<String,Map<String, Object>> resMap,String responseKey) {
		List<TravelRecordBO> results = new ArrayList<>();
		travelServicePage.forEach(tr -> {results.add(getBOFromTravelRecord(tr));});
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("totalPages", travelServicePage.getTotalPages());
		m.put("totalElements", travelServicePage.getTotalElements());
		m.put("size", travelServicePage.getSize());
		m.put("results", results);
		resMap.put(responseKey,m);
		return resMap;
	}
	
	public TravelRecordBO getTravelServiceById(long travelRecordId) {
		return getBOFromTravelRecord(travelRecordRepository.findById(travelRecordId).get());
	}

    public TravelRecordBO update(TravelRecordBO travelRecordBO) {
        TravelRecord tr = getEntityFromTravelRecordBO(travelRecordBO);
        tr.setRequester(userService.findByUserId(travelRecordBO.getRequester().getId()));
        User newApprover = userService.findByUserId(travelRecordBO.getApprover().getId());
        tr.setApprover(userService.findByUserId(travelRecordBO.getApprover().getId()));
        TravelRecord existing = travelRecordRepository.getOne(tr.getId());
        List<Notifications> list = new ArrayList<>();
        if(!existing.getStatus().equalsIgnoreCase(tr.getStatus())) {
        	Notifications n = new Notifications();
        	n.setForUser(existing.getRequester());
        	n.setNotificationText("Status of your travel request [" + existing.getTravelLocationFrom()
        	+ " to " + existing.getTravelLocationTo() + " ; " + existing.getTravelDateFrom()
        	+ " - " + existing.getTravelDateTo() + " ] has changed from " + existing.getStatus() + " to "
        	+ tr.getStatus());
        	n.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        	n.setModifiedDate(new Date(Calendar.getInstance().getTime().getTime()));
        	list.add(n);
        }
        if(!existing.getApprover().equals(newApprover)) {
        	Notifications n = new Notifications();
        	n.setForUser(existing.getApprover());
        	n.setNotificationText("You are no longer an approver for request from " + existing.getRequester().getFirst_name() 
        			+ " " + existing.getRequester().getLast_name() + " [ " 
        			+ existing.getTravelLocationFrom() + " to " + existing.getTravelLocationTo() + " ; " 
        			+ existing.getTravelDateFrom() + " - " + existing.getTravelDateTo() + " ] ");
        	n.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        	n.setModifiedDate(new Date(Calendar.getInstance().getTime().getTime()));
        	list.add(n);

        	Notifications n1 = new Notifications();
        	n1.setForUser(newApprover);
        	n1.setNotificationText("You are now an approver for request from " + existing.getRequester().getFirst_name() 
        			+ " " + existing.getRequester().getLast_name() + " [ " 
        			+ existing.getTravelLocationFrom() + " to " + existing.getTravelLocationTo() + " ; " 
        			+ existing.getTravelDateFrom() + " - " + existing.getTravelDateTo() + " ] ");
        	n1.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        	n1.setModifiedDate(new Date(Calendar.getInstance().getTime().getTime()));
        	list.add(n1);
        }
        TravelRecord updated = travelRecordRepository.saveAndFlush(tr);
        list.forEach(n -> notificationService.create(n));
        return getBOFromTravelRecord(updated);
    }
	
    /**
     * 
     * @param travelRecordBO
     * @return
     */
	public TravelRecordBO create(TravelRecordBO travelRecordBO) {
		TravelRecord tr = getEntityFromTravelRecordBO(travelRecordBO);
		tr.setId(0);
		User requester = userService.findByUserId(travelRecordBO.getRequester().getId());
		tr.setRequester(requester);
		tr.setApprover(userService.findByUserId(travelRecordBO.getApprover().getId()));
    	Notifications n = new Notifications();
    	n.setForUser(tr.getApprover());
    	n.setNotificationText(requester.getFirst_name() + " " + requester.getLast_name() 
    	+ " has created a travel request to be approved by you.");
    	n.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
    	n.setModifiedDate(new Date(Calendar.getInstance().getTime().getTime()));
    	TravelRecord updated = travelRecordRepository.saveAndFlush(tr);
    	notificationService.create(n);
		return getBOFromTravelRecord(updated);
	}
	
	private static TravelRecordBO getBOFromTravelRecord(TravelRecord entity) throws IllegalArgumentException {
		BeanMappingBuilder builder = new BeanMappingBuilder() {
		      protected void configure() {
		    	  mapping(TravelRecord.class, TravelRecordBO.class,
		    			  TypeMappingOptions.mapNull(false),
		    			  TypeMappingOptions.mapEmptyString(false));
		      }
		};
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(builder);
		TravelRecordBO destObject =  
		    mapper.map(entity, TravelRecordBO.class);
		return destObject;
	}


	
	private static TravelRecord getEntityFromTravelRecordBO(TravelRecordBO bo) throws IllegalArgumentException {
		BeanMappingBuilder builder = new BeanMappingBuilder() {
		      protected void configure() {
		    	  mapping(TravelRecordBO.class, TravelRecord.class,
		    			  TypeMappingOptions.mapNull(false),
		    			  TypeMappingOptions.mapEmptyString(false));
		      }
		};
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(builder);
		TravelRecord destObject =  
		    mapper.map(bo, TravelRecord.class);
		return destObject;
	}



}
