package com.restapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {
   @NotBlank
   @Size(min = 5, max = 50)
   private String username;
   @NotBlank
   @Size(min = 6, max = 50)
   private String password;
}
