package com.restapi.service;


import com.restapi.dto.request.LoginRequest;
import com.restapi.dto.request.RegisterRequest;
import com.restapi.dto.response.JwtResponse;
import com.restapi.model.User;

import java.util.Optional;

public interface IUserService extends IGenericService<User, String>{
   User register(RegisterRequest registerRequest);
   JwtResponse login(LoginRequest loginRequest);
   Boolean existsByUsername(RegisterRequest registerRequest);
   Boolean existsByEmail(RegisterRequest registerRequest);
}
