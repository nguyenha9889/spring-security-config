package com.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
   private String fullName;
   private String username;
   private String phone;
   private String email;
   private String password;
   private String confirmPassword;
}
