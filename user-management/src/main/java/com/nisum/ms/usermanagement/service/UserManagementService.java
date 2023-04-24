package com.nisum.ms.usermanagement.service;



import com.nisum.ms.usermanagement.business.dto.UserManagementDto;

public interface UserManagementService {
    
	//Mono<UserManagementDto> creartedUser(UserManagementDto userManagementDto);
	UserManagementDto creartedUser(UserManagementDto userManagementDto) throws Exception ;
	
}
