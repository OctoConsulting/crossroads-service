package com.gov.travelservice.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@Entity
@Table(name = "comments",schema="ts")
public class Comments  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "COMMENTS_GENERATOR", sequenceName = "TS.COMMENTS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENTS_GENERATOR")
	@Column(name="id")
	private long id;

	@Column(name="comment_text")
	private String commentText;
	
	@ManyToOne
    @JoinColumn(name="added_by")
	@JsonIgnoreProperties({"userRoles", "first_name", "last_name", "supervisor", "email", "created_date", "modified_date", "modified_by"})
	private User addedBy;

	@Column(name="created_date")
	private Date createdDate;

	@Column(name="modified_date")
	private Date modifiedDate;

	@ManyToOne
    @JoinColumn(name="travel_record_id")
	@JsonIgnoreProperties({"requester", "approver", "status", "travelDateFrom", "travelDateTo", "travelLocationFrom", "travelLocationTo", "airFare", "mileage", 
		"hotel", "rentCar", "perDiem", "comments", "createdDate", "modifiedDate", "modifiedBy"})
	private TravelRecord travelRecord;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TravelRecord getTravelRecord() {
		return travelRecord;
	}

	public void setTravelRecord(TravelRecord travelRecord) {
		this.travelRecord = travelRecord;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
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
}
