package com.gov.travelservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gov.travelservice.entity.Comments;
import com.gov.travelservice.entity.TravelRecord;
import com.gov.travelservice.entity.User;
import com.gov.travelservice.service.CommentsService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value =  "travelservice/v1/", produces = {MediaType.APPLICATION_JSON_VALUE, "application/json"})
public class CommentsController {

	@Autowired
	CommentsService commentsService;
	
	@RequestMapping(value = "comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get comments. Required request params are by and id. by can be either user or travelRecord. id is the id of user or travelrecord")
	public @ResponseBody ResponseEntity<Object> getComments(@RequestParam("by") String by, @RequestParam("id") long id)  {
		List<Comments> response; 
		if("user".equalsIgnoreCase(by)) {
			response =  commentsService.getCommentsForUser(new User(id));
		} else if("travelRecord".equalsIgnoreCase(by)) {
			TravelRecord tr = new TravelRecord();
			tr.setId(id);
			response =  commentsService.getCommentsForTravelRecord(tr);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"response\" : \"Invalid value for by. Supported values are user, travelRecord }");
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@RequestMapping(value = "comments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Save a comment")
	public @ResponseBody Comments createComment(@RequestBody Comments comment)  {
		System.out.println(comment);
		return commentsService.create(comment);
	}

	@RequestMapping(value = "comments", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update a comment")
	public @ResponseBody Comments updateComment(@RequestBody Comments comment)  {
		return commentsService.create(comment);
	}
}
