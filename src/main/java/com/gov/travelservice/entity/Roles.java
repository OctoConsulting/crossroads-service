package com.gov.travelservice.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="roles",schema="ts")
@NamedQuery(name="Roles.findAll", query="SELECT r FROM Roles r")
public class Roles implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Roles() {
	}
	
	@Id
	@Column(name="role_id")
	private long id;
	
	@Column(name="role_name")
	@JsonProperty("roleName")
	private String roleName;
	
	public Roles(String name) {
		this.roleName = name;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


}
