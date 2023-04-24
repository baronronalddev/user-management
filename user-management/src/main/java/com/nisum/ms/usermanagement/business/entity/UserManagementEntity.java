package com.nisum.ms.usermanagement.business.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Clase UserManagementEntity.
 * @author Ronald Baron.
 * @version 1.0
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user_nisum")
public class UserManagementEntity {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private UUID id;
        
    @Column(name = "name")
    private String name;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "phones")
    private String auxPhones;
    
    @Column(name = "token")
    private String token;
    
    @Column(name="created" , length = 20 , unique = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate created;
    
    @Column(name="modified" , length = 20 , unique = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modified;
    
    @Column(name="last_login" , length = 20 , unique = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate last_login;
    
    @Column(name = "isactive")
    private boolean isactive;
    
}

