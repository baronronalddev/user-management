package com.nisum.ms.usermanagement.expose;

import javax.persistence.NonUniqueResultException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nisum.ms.usermanagement.exception.ErrorDto;

/**
 * ControllerAdvice.
 * @author Ronald Baron.
 * @version 1.0
 */
@RestControllerAdvice
public class ControllerAdvice {
	
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ErrorDto> runTimeExceptionHandler(RuntimeException ex) {
		ErrorDto errorDto = ErrorDto.builder()
				                    //.code("P-500")
				                    .message(ex.getMessage())
				                    .build();
		
		return new ResponseEntity<>(errorDto ,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorDto> exceptionHandler(Exception ex) {
		ErrorDto errorDto = ErrorDto.builder()
				                    //.code("P-500")
				                    .message(ex.getMessage())
				                    .build();
		
		return new ResponseEntity<>(errorDto ,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NonUniqueResultException.class)
	public ResponseEntity<ErrorDto> nonUniqueResulexceptionHandler(NonUniqueResultException ex) {
		ErrorDto errorDto = ErrorDto.builder()
				                    //.code("P-500")
				                    .message(ex.getMessage())
				                    .build();
		
		return new ResponseEntity<>(errorDto ,HttpStatus.CONFLICT);
	}
	

}
