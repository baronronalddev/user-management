package com.nisum.ms.usermanagement.expose;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.nisum.ms.usermanagement.business.dto.UserManagementDto;
import com.nisum.ms.usermanagement.jwt.JwtTokenProvider;
import com.nisum.ms.usermanagement.model.JwtRequest;
import com.nisum.ms.usermanagement.model.JwtResponse;
import com.nisum.ms.usermanagement.model.UserManagementResponse;
import com.nisum.ms.usermanagement.service.UserManagementService;
import com.nisum.ms.usermanagement.util.UserManagementConstantes;
import com.nisum.ms.usermanagement.util.UserManagementUtil;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserManagementController.
 * @author Ronald Baron.
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class UserManagementController {
    
    @Autowired
    private UserManagementService userManagementService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider jwtTokenUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @GetMapping
    public String welcome() {
    	return "Bienvenidos al ms de user management";
    }
    
    /**
     * Metodo para crear usarios.
     */
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = UserManagementDto.class, responseContainer = "Object"),
        @ApiResponse(code = 400, message = "Error", response = Exception.class),
        @ApiResponse(code = 401, message = "Acceso no autorizado", response = Exception.class),
        @ApiResponse(code = 500, message = "Ocurrio un error", response = Exception.class) })
    @PostMapping("/createdUser")
    public ResponseEntity<UserManagementResponse> saveUserManagement(@RequestBody UserManagementDto userManagementDto) throws Exception {
        log.info(" UserManagementController.saveUserManagement");
       
        UserManagementResponse response = UserManagementUtil.mapperClass(
        		UserManagementResponse.class, userManagementService.creartedUser(userManagementDto));
        
        return new ResponseEntity<UserManagementResponse>(
        		response, HttpStatus.CREATED);
    }
   
	 /**
     * Metodo para crear token.
     */
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = JwtRequest.class, responseContainer = "Object"),
        @ApiResponse(code = 400, message = "Error", response = Exception.class),
        @ApiResponse(code = 401, message = "Acceso no autorizado", response = Exception.class),
        @ApiResponse(code = 500, message = "Ocurrio un error", response = Exception.class) })
    @PostMapping(value = "/token", produces = UserManagementConstantes.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest)
            throws Exception {
        log.info("UserManagementController.createAuthenticationToken");
        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
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
