package com.gov.travelservice.repository;

import java.util.List;

import javax.persistence.OrderBy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import com.gov.travelservice.entity.TravelRecord;
import com.gov.travelservice.entity.User;


public interface TravelRecordRepository extends JpaRepository<TravelRecord, Long>, JpaSpecificationExecutor<TravelRecord> {

	Page<TravelRecord> findAll(@Nullable Specification<TravelRecord> spec, Pageable pageable);
	List<TravelRecord> findByRequester(User user);
//	Page<TravelRecord> findAll(Pageable pageable);
	Page<TravelRecord> findAll(Pageable pageable);
	
	@OrderBy("date_from ASC")
	Page<TravelRecord> findByRequester(User user, Pageable pageable);
	
	@OrderBy("date_from ASC")
	Page<TravelRecord> findByRequesterAndStatus(User user, String status,Pageable pageable);
	
	@OrderBy("date_from ASC")
	Page<TravelRecord> findByApproverAndStatus(User userreq, String string, Pageable pageable);
	
}
