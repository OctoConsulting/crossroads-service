package com.gov.travelservice.pojo;

import java.io.Serializable;

/**
 * 
 * @author vinodcherukuri
 *
 */
public class TravelRecordHistory implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private long id;
	private long travelRecordId;
	private long userId;
	private String userName;
	private String comments;
	private String createdDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTravelRecordId() {
		return travelRecordId;
	}
	public void setTravelRecordId(long travelRecordId) {
		this.travelRecordId = travelRecordId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
