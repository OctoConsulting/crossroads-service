package com.gov.travelservice.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "notifications",schema="ts")
public class Notifications  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "NOTIFICATIONS_GENERATOR", sequenceName = "TS.NOTIFICATIONS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTIFICATIONS_GENERATOR")
	@Column(name="id")
	private long id;
	
	@ManyToOne
    @JoinColumn(name="for_user")
	@JsonIgnoreProperties({"userRoles", "first_name", "last_name", "supervisor", "email", "created_date", "modified_date", "modified_by"})
	private User forUser;

	@Column(name="notification_text")
	private String notificationText;
	
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="modified_date")
	private Date modifiedDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getForUser() {
		return forUser;
	}

	public void setForUser(User forUser) {
		this.forUser = forUser;
	}

	public String getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
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
