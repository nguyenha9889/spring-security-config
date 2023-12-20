package com.restapi.repository;

import com.restapi.model.RefreshToken;
import com.restapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
   Optional<RefreshToken> findByToken(String token);
   int deleteByUser(User user);
}
