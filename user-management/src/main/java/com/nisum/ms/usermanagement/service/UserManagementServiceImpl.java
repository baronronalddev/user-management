package com.nisum.ms.usermanagement.service;

import com.google.gson.Gson;
import com.nisum.ms.usermanagement.business.dto.UserManagementDto;
import com.nisum.ms.usermanagement.business.entity.UserManagementEntity;
import com.nisum.ms.usermanagement.business.repository.UserManagementRepository;
import com.nisum.ms.usermanagement.jwt.JwtTokenProvider;
import com.nisum.ms.usermanagement.util.UserManagementConstantes;
import com.nisum.ms.usermanagement.util.UserManagementUtil;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Clase UserManagementService.
 * @author Ronald Baron.
 * @version 1.0
 */
@Slf4j
@Service
public class UserManagementServiceImpl implements UserManagementService {
    
    @Autowired
    private UserManagementRepository userManagementRespository;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider jwtTokenUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
    

	@Override
	public UserManagementDto creartedUser(UserManagementDto userManagementDto) throws Exception {
		log.info("UserManagementServiceImpl.creartedUser");
		
		if(userManagementRespository.existsUser(userManagementDto.getEmail())) {
			throw new RuntimeException(UserManagementConstantes.MENSAJE_CONTENT);
		} 
		
        if(userManagementDto.getEmail()==null) {
        	throw new Exception(UserManagementConstantes.EMAIL_REQUERID);
        }
	    // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        
        Matcher mather = pattern.matcher(userManagementDto.getEmail());
       
        if(mather.find() == false || userManagementDto.getEmail()==null) {
        	throw new RuntimeException(UserManagementConstantes.EMAIL_INVALID);
        } 
                
		UserManagementEntity userManagementEntity = UserManagementUtil
														.mapperClass(UserManagementEntity.class, userManagementDto);
		
		log.info("UserManagementEntity : " + userManagementEntity);
		log.info("UserManagementEntity.id : " + userManagementEntity.getId());
		
		createdUserManagementEntity(userManagementEntity, userManagementDto);
		
		UserManagementEntity  newUserManagementEntity = userManagementRespository
															.save(userManagementEntity);
		log.info("Guardamos los datos:" + newUserManagementEntity);
	
		//Convertimos dto a entidad
		UserManagementDto userManagementResponseDto = getUserManagementDto(newUserManagementEntity);
		return userManagementResponseDto;
   }
	
	
   private void createdUserManagementEntity(UserManagementEntity entity, UserManagementDto dto ) throws Exception {
	   log.info("UserManagementServiceImpl.createdUserManagementEntity");
	    Gson gson = new Gson();
		entity.setAuxPhones(gson.toJson(dto.getPhones()));
		entity.setToken(generatedSasToken());
		entity.setCreated(LocalDate.now());
		entity.setModified(LocalDate.now());
		entity.setLast_login(LocalDate.now());
		entity.setIsactive(true);
   }
	

	
   private UserManagementDto getUserManagementDto(UserManagementEntity entity ) throws Exception {
	   UserManagementDto dto = UserManagementUtil
				.mapperClass(UserManagementDto.class, entity);
	   //dto.setId(UserManagementUtil.generarUuid());
	   String phoneAux = dto.getAuxPhones();
	   if(!UserManagementUtil.validarStringEmpty(phoneAux)) {
		   dto.setPhones(dto.mapperPhone(phoneAux));
	   }
	   return dto;
   }
   
   private String generatedSasToken() throws Exception {
	   authenticate("ronald", "admin");
       UserDetails userDetails = userDetailsService.loadUserByUsername("ronald");
       String token = jwtTokenUtil.generateToken(userDetails);
	   return token;
   }
   
   private void authenticate(String username, String password) throws Exception {
       log.info("UserManagementController.authenticate");
       try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
       } catch (DisabledException e) {
           log.info("UserManagementController.DisabledException");
           throw new Exception("USER_DISABLED", e);
       } catch (BadCredentialsException e) {
           log.info("UserManagementController.BadCredentialsException");
           throw new Exception("INVALID_CREDENTIALS", e);
       }
   }
   
}
