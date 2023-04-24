package com.nisum.ms.usermanagement.model;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase .
 * @author Ronald Barón.
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class UserManagementResponse {

	public UUID id;
	private LocalDate created;
	private LocalDate modified;
	private LocalDate last_login;
	private String token;
	private boolean isactive;
	
	
}
