package com.restapi.repository;

import com.restapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
   Optional<User> findByUsernameOrEmail(String username, String email);
   Boolean existsByUsernameOrEmail(String username, String email);

}
