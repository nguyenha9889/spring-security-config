package com.restapi.security.jwt;

import com.restapi.model.User;
import com.restapi.security.principal.UserDetailCustom;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * Lớp này có 3 chức năng chính:
 * generate JWT, refresh Token
 * get JWT, refresh Token from Cookies
 * validate a JWT: JWT Access Token is expired with ExpiredJwtException
 */
@Component
@Slf4j // ghi log
public class JwtUtils {
   @Value("${jwt.secret-key}")
   private String jwtSecret;
   @Value("${jwt.cookie-name}")
   private String jwtCookie;
   @Value("${jwt.refresh-cookie-name}")
   private String jwtRefreshCookie;
   @Value("${jwt.expired.access-token}")
   private long expiredAccess;

   public ResponseCookie generateJwtCookie(User user) {
      String jwtToken = generateTokenFromUsername(user.getUsername());
      return generateCookie(jwtCookie, jwtToken, "/api/v1/auth");
   }

   public ResponseCookie generateJwtCookie(UserDetailCustom userDetail) {
      String jwtToken = generateTokenFromUsername(userDetail.getUsername());
      return generateCookie(jwtCookie, jwtToken, "/api/v1/auth");
   }

   public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
      return generateCookie(jwtRefreshCookie, refreshToken, "/api/v1/auth/refresh-token");
   }

   public String getJwtFromCookies(HttpServletRequest request) {
      return getCookieValueByName(request, jwtCookie);
   }

   public String getJwtRefreshFromCookies(HttpServletRequest request) {
      return getCookieValueByName(request, jwtRefreshCookie);
   }

   public ResponseCookie getCleanJwtCookie() {
      return ResponseCookie.from(jwtCookie, null).path("/api/v1").build();
   }

   public ResponseCookie getCleanJwtRefreshCookie() {
      return ResponseCookie.from(jwtRefreshCookie, null).path("/api/v1/auth/refresh-token").build();
   }

   public String getUserNameFromJwtToken(String token) {
      return Jwts.parserBuilder().setSigningKey(key()).build()
            .parseClaimsJws(token).getBody().getSubject();
   }

   public boolean validateJwtToken(String authToken) {
      try {
         Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
         return true;
      } catch (SignatureException e) {
         log.error("Invalid JWT signature: {}", e.getMessage());
      } catch (MalformedJwtException e) {
         log.error("Invalid JWT token: {}", e.getMessage());
      } catch (ExpiredJwtException e) {
         log.error("JWT token is expired: {}", e.getMessage());
      } catch (UnsupportedJwtException e) {
         log.error("JWT token is unsupported: {}", e.getMessage());
      } catch (IllegalArgumentException e) {
         log.error("JWT claims string is empty: {}", e.getMessage());
      }

      return false;
   }

   private Key key() {
      byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
      return Keys.hmacShaKeyFor(keyBytes);
   }
   public String generateTokenFromUsername(String username) {
      return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + expiredAccess))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
   }

   private ResponseCookie generateCookie(String name, String value, String path) {
      return ResponseCookie.from(name, value).path(path).maxAge(24 * 60 * 60).httpOnly(true).build();
   }

   private String getCookieValueByName(HttpServletRequest request, String name) {
      Cookie cookie = WebUtils.getCookie(request, name);
      if (cookie != null) {
         return cookie.getValue();
      } else {
         return null;
      }
   }

}
