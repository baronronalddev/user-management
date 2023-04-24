package com.nisum.ms.usermanagement.exception;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ErrorDto {
   
    @JsonProperty("mensaje")
	private String message;
    
    
}
