package com.restapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
   @NotBlank
   private String fullName;

   @NotBlank
   @Size(min = 5, max = 20)
   private String username;

   @NotBlank
   @Email
   @Size(max = 50)
   private String email;

   @NotBlank
   @Size(min = 9, max = 15)
   private String phone;

   @NotBlank
   @Size(min = 6, max = 50)
   private String password;
   private Boolean gender;
   private Date birthday;

   private Set<String> roles;
}
