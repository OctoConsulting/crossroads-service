package com.gov.travelservice.repository;

import java.util.List;

import javax.persistence.OrderBy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import com.gov.travelservice.entity.Comments;
import com.gov.travelservice.entity.TravelRecord;
import com.gov.travelservice.entity.User;

public interface CommentsRepository extends JpaRepository<Comments, Long>, JpaSpecificationExecutor<Comments> {

	Page<Comments> findAll(@Nullable Specification<Comments> spec, Pageable pageable);
	List<Comments> findByTravelRecord(TravelRecord travelRecord);
	List<Comments> findByAddedBy(User user);
	Page<Comments> findAll(Pageable pageable);
	
	@OrderBy("date_from ASC")
	Page<Comments> findByTravelRecord(TravelRecord travelRecord, Pageable pageable);

	@OrderBy("date_from ASC")
	Page<Comments> findByAddedBy(User user, Pageable pageable);
}
