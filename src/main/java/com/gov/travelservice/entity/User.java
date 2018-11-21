package com.gov.travelservice.entity;

import java.io.Serializable;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user",schema="ts")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public User() {
		
	}
	
	public User(long id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "USER_GENERATOR", sequenceName = "TS.USER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GENERATOR")
	@Column(name="id")
	private long id;
	
	@Column(name="first_name")
	private String first_name;
	
	@Column(name="last_name")
	private String last_name;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	@JsonIgnore
	private String password;
	
	@Column(name="supervisor")
	private long supervisor;
	
	@Column(name="email")
	private String email;
	
	@Column(name="created_date")
	private Date created_date;
	
	@Column(name="modified_date")
	private Date modified_date;
	
	@Column(name="modified_by")
	private String modified_by;
	
	@Column(name="groupid")
	private int groupId;
	
	@Transient
	private int tRoleId;

	//bi-directional many-to-one association to UserRole
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id.user", orphanRemoval = true)
	private List<UserRoles> userRoles;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(long supervisor) {
		this.supervisor = supervisor;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getModified_date() {
		return modified_date;
	}

	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}

	public List<UserRoles> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int gettRoleId() {
		return tRoleId;
	}

	public void settRoleId(int tRoleId) {
		this.tRoleId = tRoleId;
	}

	
}
