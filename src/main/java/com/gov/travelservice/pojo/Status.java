package com.gov.travelservice.pojo;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="travel_status")
@Table(name="travel_status",schema="ts")
public class Status implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private long id;
	
	private String status;
	
	/*@OneToOne(mappedBy="status")
	@JsonBackReference
	private TravelRecord travelRecord;*/
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
