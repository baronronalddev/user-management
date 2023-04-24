package com.nisum.ms.usermanagement.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Clase UserManagementDto.
 * @author Ronald Baron.
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserManagementDto {
    
   public UUID id;
   private String name;
   private String email;
   private String password;
   private List<Phone> phones;
   private String auxPhones;
   
   @JsonFormat(pattern = "dd-MM-yyyy")
   private LocalDate created;
   
   @JsonFormat(pattern = "dd-MM-yyyy")
   private LocalDate modified;
   
   private String token;
   private boolean isactive;
   
   @JsonFormat(pattern = "dd-MM-yyyy")
   private LocalDate last_login;
   
   

   /**
    * Clase phoneList.
    * @author Ronald Baron.
    * @version 1.0
    */
   @Getter
   @Setter
   @NoArgsConstructor
   public static class Phone {
       private String number;
       private String citycode;
       private String contrycode;
   }
   
   public List<Phone> mapperPhone(String json) {
       Type typeList = new TypeToken<List<Phone>>(){}.getType();
       return new Gson().fromJson(json, typeList);
   }
   
   
}
