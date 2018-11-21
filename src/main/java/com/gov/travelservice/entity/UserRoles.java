package com.gov.travelservice.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user_roles",schema="ts")
public class UserRoles implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserRolesPK id;
	
	public UserRoles() {
	}
	
	public UserRoles(User user, Roles role) {
		id = new UserRolesPK();
		id.setUser(user);
		id.setRole(role);
	}
	
	public UserRolesPK getId() {
		return this.id;
	}

	public void setId(UserRolesPK id) {
		this.id = id;
	}
}
