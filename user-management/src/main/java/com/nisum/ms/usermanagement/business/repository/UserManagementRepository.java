package com.nisum.ms.usermanagement.business.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nisum.ms.usermanagement.business.entity.UserManagementEntity;

@Repository
public interface UserManagementRepository extends CrudRepository<UserManagementEntity,Long> {
    
	@Query("select case when count(c)> 0 then true else false end from UserManagementEntity c where lower(c.email) like lower(:email)")
	boolean existsUser(@Param("email") String email);
		
}