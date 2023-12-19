package com.restapi.service;


import com.restapi.dto.RegisterDTO;
import com.restapi.entity.Role;
import com.restapi.entity.RoleName;
import com.restapi.entity.User;

public interface IUserService extends IGenericService<User, Long> {
   User findByUsername(String username);
   User create(RegisterDTO registerDTO, Role role);
}
