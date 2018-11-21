package com.gov.travelservice.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gov.travelservice.entity.Comments;
import com.gov.travelservice.entity.User;

public class TravelRecordBO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

//	@JsonBackReference(value="requester")
	@JsonIgnoreProperties({"userRoles", "first_name", "last_name", "supervisor", "email", "created_date", "modified_date", "modified_by"})
	private User requester;

//	@JsonBackReference(value="approver")
	@JsonIgnoreProperties({"userRoles", "first_name", "last_name", "supervisor", "email", "created_date", "modified_date", "modified_by"})
	private User approver;

	private String status;

	private Date travelDateFrom;

	private Date travelDateTo;

	private String travelLocationFrom;

	private String travelLocationTo;

	private Double airFare;

	private Double mileage;

	private Double hotel;

	private Double rentCar;

	private Double perDiem;

	private List<Comments> comments;

	private Date createdDate;

	private Date modifiedDate;

	private String modifiedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTravelDateFrom() {
		return travelDateFrom;
	}

	public void setTravelDateFrom(Date travelDateFrom) {
		this.travelDateFrom = travelDateFrom;
	}

	public Date getTravelDateTo() {
		return travelDateTo;
	}

	public void setTravelDateTo(Date travelDateTo) {
		this.travelDateTo = travelDateTo;
	}

	public String getTravelLocationFrom() {
		return travelLocationFrom;
	}

	public void setTravelLocationFrom(String travelLocationFrom) {
		this.travelLocationFrom = travelLocationFrom;
	}

	public String getTravelLocationTo() {
		return travelLocationTo;
	}

	public void setTravelLocationTo(String travelLocationTo) {
		this.travelLocationTo = travelLocationTo;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public User getRequester() {
		return requester;
	}

	public void setRequester(User requester) {
		this.requester = requester;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

	public Double getAirFare() {
		return airFare;
	}

	public void setAirFare(Double airFare) {
		this.airFare = airFare;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public Double getHotel() {
		return hotel;
	}

	public void setHotel(Double hotel) {
		this.hotel = hotel;
	}

	public Double getRentCar() {
		return rentCar;
	}

	public void setRentCar(Double rent_car) {
		this.rentCar = rent_car;
	}

	public Double getPerDiem() {
		return perDiem;
	}

	public void setPerDiem(Double per_diem) {
		this.perDiem = per_diem;
	}

}
