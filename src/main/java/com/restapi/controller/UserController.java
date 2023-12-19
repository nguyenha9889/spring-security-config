package com.restapi.controller;


import com.restapi.dto.RegisterDTO;
import com.restapi.entity.Role;
import com.restapi.entity.RoleName;
import com.restapi.entity.User;
import com.restapi.service.IRoleService;
import com.restapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
   private final IUserService userService;
   private final IRoleService roleService;
   @GetMapping("/users")
   public List<User> findAll(){
      return userService.findAll();
   }

   @PostMapping("/auth/register")
   public ResponseEntity<String> doRegister(@RequestBody RegisterDTO register){
      Role role = roleService.findByRoleName(RoleName.ROLE_USER);
      User user = userService.create(register, role);
      userService.save(user);
      return new ResponseEntity<>("Register successfully", HttpStatus.OK);
   }
}
