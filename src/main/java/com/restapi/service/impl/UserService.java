package com.restapi.service.impl;

import com.restapi.dto.request.LoginRequest;
import com.restapi.dto.request.RegisterRequest;
import com.restapi.dto.response.JwtResponse;
import com.restapi.exception.ResourceNotFoundException;
import com.restapi.model.RefreshToken;
import com.restapi.model.Role;
import com.restapi.model.RoleName;
import com.restapi.model.User;
import com.restapi.repository.IUserRepository;
import com.restapi.security.jwt.JwtUtils;
import com.restapi.security.principal.UserDetailCustom;
import com.restapi.service.IRoleService;
import com.restapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
   private final IUserRepository userRepository;
   private final IRoleService roleService;
   private final AuthenticationManager authManager;
   private final JwtUtils jwtUtils;
   private final RefreshTokenService refreshTokenService;
   private final PasswordEncoder passwordEncoder;

   @Override
   public User register(RegisterRequest registerRequest) {

      Set<String> strRoles = registerRequest.getRoles();
      Set<Role> roles = new HashSet<>();
      if (strRoles == null) {
         Role userRole = roleService.findByRoleName(RoleName.ROLE_USER).orElseThrow(
               () -> new ResourceNotFoundException("Role not found with roleName = " + RoleName.ROLE_USER)
         );
         roles.add(userRole);
      } else {
         strRoles.forEach(role -> {
            switch (role) {
               case "admin":
                  Role adminRole = roleService.findByRoleName(RoleName.ROLE_ADMIN).orElseThrow(
                        () -> new ResourceNotFoundException("Role not found with roleName = " + RoleName.ROLE_ADMIN)
                  );
                  roles.add(adminRole);
               case "pm":
                  Role pmRole = roleService.findByRoleName(RoleName.ROLE_PM).orElseThrow(
                        () -> new ResourceNotFoundException("Role not found with roleName = " + RoleName.ROLE_PM)
                  );
                  roles.add(pmRole);
               default:
                  Role userRole = roleService.findByRoleName(RoleName.ROLE_USER).orElseThrow(
                        () -> new ResourceNotFoundException("Role not found with roleName = " + RoleName.ROLE_USER)
                  );
                  roles.add(userRole);
            }
         });
      }

      return User.builder()
            .fullName(registerRequest.getFullName())
            .username(registerRequest.getUsername())
            .email(registerRequest.getEmail())
            .phone(registerRequest.getPhone())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .roles(roles)
            .build();
   }

   @Override
   public JwtResponse login(LoginRequest loginRequest) {
      Authentication auth = null;
      try {
         auth = authManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
         );
      }catch (RuntimeException e) {
         throw new ResourceNotFoundException("Username or password incorrect");
      }
      UserDetailCustom userDetail = (UserDetailCustom) auth.getPrincipal();
      List<String> roles = userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

      String accessToken = jwtUtils.generateJwtCookie(userDetail).toString();
      RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetail.getId());

      return JwtResponse.builder()
            .username(userDetail.getUsername())
            .email(userDetail.getEmail())
            .roles(roles)
            .accessToken(accessToken)
            .refreshToken(refreshToken.getToken())
            .build();
   }


   @Override
   public void save(User user) {
      userRepository.save(user);
   }

   @Override
   public Boolean existsByUsername(RegisterRequest registerRequest) {
      return userRepository.existsByUsername(registerRequest.getUsername());
   }

   @Override
   public Boolean existsByEmail(RegisterRequest registerRequest) {
      return userRepository.existsByEmail(registerRequest.getEmail());
   }

}
