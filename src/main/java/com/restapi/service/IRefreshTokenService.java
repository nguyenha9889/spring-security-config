package com.restapi.service;

import com.restapi.model.RefreshToken;

public interface IRefreshTokenService extends IGenericService<RefreshToken, Long> {
   RefreshToken findByToken(String token);
   RefreshToken createRefreshToken(String userId);
   boolean verifyExpiration(RefreshToken token);
   void deleteByUser(String userId);
   RefreshToken generateRefreshToken(String token);
}
