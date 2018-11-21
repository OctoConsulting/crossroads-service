package com.gov.travelservice.repository;

import java.util.List;

import javax.persistence.OrderBy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import com.gov.travelservice.entity.Notifications;
import com.gov.travelservice.entity.User;

public interface NotificationsRepository  extends JpaRepository<Notifications, Long>, JpaSpecificationExecutor<Notifications> {

	Page<Notifications> findAll(@Nullable Specification<Notifications> spec, Pageable pageable);
	List<Notifications> findByForUser(User user);
	Page<Notifications> findAll(Pageable pageable);
	
	@OrderBy("date_from ASC")
	Page<Notifications> findByForUser(User user, Pageable pageable);
}
