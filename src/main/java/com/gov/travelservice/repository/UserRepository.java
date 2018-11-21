package com.gov.travelservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gov.travelservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{

	public User findById(long id);

	public User findByUsername(String username);

	@Modifying
	@Query(value = "update ts.user set password=?2 where id=?1 ", nativeQuery = true)
	public void resetPassword(long userid, String password);
}
