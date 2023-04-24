package com.nisum.ms.usermanagement.service;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Clase UserDetailsService.
 * @author Ronald Baron.
 * @version 1.0
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsServiceImpl.loadUserByUsername");
        if ("ronald".equals(username)) {
            String password = "admin"; 
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);  
            log.info("UserDetailsServiceImpl.loadUserByUsername.password"+encodedPassword);
            return new User(
                    "ronald", 
                    "$2a$10$egQwB381/H00Dk0hxmGJg.Qsv4Q62gtfvYZ.8Oi56yo4uVJgtTkYa",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado con nombre : " + username);
        }
    }
    
}
