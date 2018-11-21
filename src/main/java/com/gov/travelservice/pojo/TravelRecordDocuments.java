package com.gov.travelservice.pojo;

import java.io.Serializable;

public class TravelRecordDocuments implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private long travelRecordId;
	private String fileName;
	private String url;
	private String createDate;
	
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
}
