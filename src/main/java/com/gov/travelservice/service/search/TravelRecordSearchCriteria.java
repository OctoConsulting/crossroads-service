package com.gov.travelservice.service.search;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.gov.travelservice.entity.User;

public class TravelRecordSearchCriteria extends SearchCriteria {
	public TravelRecordSearchCriteria() {
		super();
	}

	private User requester;

	private User approver;

	private String status;

	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date travelDateFrom;

	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date travelDateTo;

	private String travelLocationFrom;

	private String travelLocationTo;

	private Double airFare;

	private Double mileage;

	private Double hotel;

	private Double rentCar;

	private Double perDiem;

	private String comments;



	public TravelRecordSearchCriteria(Integer page, Integer pageSize, String sortOrder, String sortField) {
		super(page, pageSize, sortOrder, sortField);
	}

	public TravelRecordSearchCriteria(Integer pageSize, String sortOrder, String sortField) {
		super(pageSize, sortOrder, sortField);
	}

	public TravelRecordSearchCriteria(String sortOrder, String sortField) {
		super(sortOrder, sortField);
	}

	public TravelRecordSearchCriteria(Integer pageSize) {
		super(pageSize);
	}

	public User getRequester() {
		return requester;
	}

	public void setRequester(User requester) {
		this.requester = requester;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public void setRentCar(Double rentCar) {
		this.rentCar = rentCar;
	}

	public Double getPerDiem() {
		return perDiem;
	}

	public void setPerDiem(Double perDiem) {
		this.perDiem = perDiem;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}	
}
