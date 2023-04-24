package com.nisum.ms.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase JwtRequest.
 * @author Ronald Baron.
 * @version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
    
    private String username;
    private String password;
    
}
