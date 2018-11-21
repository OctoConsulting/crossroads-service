package com.gov.travelservice.pojo;

import java.io.Serializable;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="travel_categories")
@Table(name="travel_categories",schema="ts")
public class Categories implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private long id;
	
	@Column(name="category_name")
	private String categoryName;
	
	@Column(name="cost_threshold")
	private double costThreshold;
	
	/*@OneToOne(mappedBy="categories")
	@JsonBackReference
	private TravelRecord travelRecord;*/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public double getCostThreshold() {
		return costThreshold;
	}

	public void setCostThreshold(double costThreshold) {
		this.costThreshold = costThreshold;
	}

	/*public TravelRecord getTravelRecord() {
		return travelRecord;
	}

	public void setTravelRecord(TravelRecord travelRecord) {
		this.travelRecord = travelRecord;
	}
	*/
	

	

}
