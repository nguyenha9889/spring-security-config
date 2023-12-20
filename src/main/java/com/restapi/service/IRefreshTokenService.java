package com.restapi.service;

import com.restapi.model.RefreshToken;

import java.util.Optional;

public interface IRefreshTokenService extends IGenericService<RefreshToken, Long> {
   RefreshToken findByToken(String token);
   RefreshToken createRefreshToken(Long userId);
   RefreshToken verifyExpiration(RefreshToken token);
}
