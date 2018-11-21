package com.gov.travelservice.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gov.travelservice.entity.Comments;
import com.gov.travelservice.entity.TravelRecord;
import com.gov.travelservice.entity.User;
import com.gov.travelservice.repository.CommentsRepository;

@Service
public class CommentsService {

	@Autowired
	CommentsRepository commentsRepository; 
	
	public List<Comments> getCommentsForTravelRecord(TravelRecord travelRecord) {
		return commentsRepository.findByTravelRecord(travelRecord);
	}
	
	public List<Comments> getCommentsForUser(User user) {
		return commentsRepository.findByAddedBy(user);
	}

	public Comments create(Comments c) {
		c.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
		c.setModifiedDate(new Date(Calendar.getInstance().getTime().getTime()));
		commentsRepository.saveAndFlush(c);
		return c;
	}

	public Comments update(Comments c) {
		c.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
		c.setModifiedDate(new Date(Calendar.getInstance().getTime().getTime()));
		commentsRepository.saveAndFlush(c);
		return c;
	}
}
