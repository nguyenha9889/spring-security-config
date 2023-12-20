package com.restapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
   private String username;
   private String email;
   private List<String> roles;
   private String token;
   private final String type = "Bearer";
}
