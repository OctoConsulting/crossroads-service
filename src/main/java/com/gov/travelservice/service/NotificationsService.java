package com.gov.travelservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gov.travelservice.entity.Notifications;
import com.gov.travelservice.entity.User;
import com.gov.travelservice.repository.NotificationsRepository;

@Service
public class NotificationsService {

	@Autowired
	NotificationsRepository notificationsRepository; 
	
	public List<Notifications> getNotificationsForUser(User user) {
		return notificationsRepository.findByForUser(user);
	}
	
	public Notifications create(Notifications n) {
		notificationsRepository.saveAndFlush(n);
		return n;
	}

	public Notifications update(Notifications n) {
		notificationsRepository.saveAndFlush(n);
		return n;
	}
}
