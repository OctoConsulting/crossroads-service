package com.gov.travelservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gov.travelservice.entity.Notifications;
import com.gov.travelservice.entity.User;
import com.gov.travelservice.service.NotificationsService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value =  "travelservice/v1/", produces = {MediaType.APPLICATION_JSON_VALUE, "application/json"})
public class NotificationsController {

	@Autowired
	NotificationsService notificationService;
	
	@RequestMapping(value = "notifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get notifications for a user")
	public @ResponseBody List<Notifications> getNotifications(@RequestParam("userid") long userid)  {
		List<Notifications> response =  notificationService.getNotificationsForUser(new User(userid));
		return response;
	}

	@RequestMapping(value = "notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create a notification")
	public @ResponseBody Notifications createNotification(@RequestBody Notifications notifications)  {
		return notificationService.create(notifications);
	}

	@RequestMapping(value = "notifications", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update a notification")
	public @ResponseBody Notifications updateNotification(@RequestBody Notifications notifications)  {
		return notificationService.create(notifications);
	}
}
