package com.restapi.service.impl;

import com.restapi.exception.TokenRefreshException;
import com.restapi.model.RefreshToken;
import com.restapi.model.User;
import com.restapi.repository.IRefreshTokenRepository;
import com.restapi.repository.IUserRepository;
import com.restapi.service.IRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.restapi.exception.ResourceNotFoundException;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements IRefreshTokenService {
   @Value("${jwt.expired.refresh-token}")
   private Long expiredRefreshToken;
   private final IRefreshTokenRepository refreshTokenRepository;
   private final IUserRepository userRepository;
   @Override
   public int delete(Long userId) {
      User user = userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User not found")
      );
      return refreshTokenRepository.deleteByUser(user) ;
   }

   @Override
   public RefreshToken findByToken(String token) {
      return refreshTokenRepository.findByToken(token).orElseThrow(
            () -> new ResourceNotFoundException("Token is incorrect")
      );
   }

   @Override
   public void save(RefreshToken refreshToken) {
      refreshTokenRepository.save(refreshToken) ;
   }

   @Override
   public RefreshToken createRefreshToken(Long userId) {
      return RefreshToken.builder()
            .id(null)
            .user(userRepository.findById(userId).get())
            .token(UUID.randomUUID().toString())
            .expiryDate(Instant.now().plusMillis(expiredRefreshToken))
            .build();
   }

   @Override
   public RefreshToken verifyExpiration(RefreshToken token) {
      if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
         refreshTokenRepository.delete(token);
         throw new TokenRefreshException(token.getToken(),
               "Refresh token was expired. Please make a new sign-in request");
      }

      return token;
   }
}
