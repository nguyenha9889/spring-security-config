package com.restapi.controller;


import com.restapi.dto.request.LoginRequest;
import com.restapi.dto.request.RegisterRequest;
import com.restapi.dto.response.JwtResponse;
import com.restapi.model.RefreshToken;
import com.restapi.model.User;
import com.restapi.security.jwt.JwtUtils;
import com.restapi.service.IRefreshTokenService;
import com.restapi.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {

   private final IUserService userService;
   private final IRefreshTokenService refreshTokenService;
   private final JwtUtils jwtUtils;

   @PostMapping("/register")
   public ResponseEntity<?> doRegister(@Validated @RequestBody RegisterRequest registerRequest){
      if (userService.existsByUsername(registerRequest)) {
         return ResponseEntity
               .badRequest()
               .body("Error: Username is already existed");
      }
      if (userService.existsByEmail(registerRequest)) {
         return ResponseEntity
               .badRequest()
               .body("Error: Email is already existed");
      }
      User user = userService.register(registerRequest);
      userService.save(user);
      return ResponseEntity.ok("User registered successfully");
   }

   @PostMapping("/login")
   public ResponseEntity<?> doLogin(@Validated @RequestBody LoginRequest loginRequest){
      JwtResponse jwtResponse = userService.login(loginRequest);
      ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(jwtResponse.getRefreshToken());
      return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtResponse.getAccessToken())
            .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
            .body(jwtResponse);
   }

   @PostMapping("/refresh-token")
   public ResponseEntity<?> refreshToken(HttpServletRequest request){
      String strToken = jwtUtils.getJwtRefreshFromCookies(request);
      if (strToken != null && !strToken.isEmpty()) {
         RefreshToken refreshToken = refreshTokenService.generateRefreshToken(strToken);
         refreshTokenService.save(refreshToken);
         User user = userService.findById(refreshToken.getUser().getId());
         ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
         return ResponseEntity.ok()
               .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
               .body("Refresh Token is refreshed successfully");
      }
      return ResponseEntity.badRequest().body("Refresh Token is incorrect");
   }
}
