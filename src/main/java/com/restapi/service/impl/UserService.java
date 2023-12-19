package com.restapi.service.impl;

import com.restapi.dto.RegisterDTO;
import com.restapi.entity.Role;
import com.restapi.entity.User;
import com.restapi.exception.NoSuchElementException;
import com.restapi.repository.IUserRepository;
import com.restapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
   private final IUserRepository userRepository;

   @Override
   public List<User> findAll() {
      return userRepository.findAll();
   }

   @Override
   public User findById(Long id) {
      return userRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Not found user id :: " + id)
      );
   }

   @Override
   public void save(User user) {
      userRepository.save(user);
   }

   @Override
   public void delete(Long id) {
      User user = userRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Not found user id :: " + id));
      if (user != null){
         userRepository.delete(user);
      }
   }

   @Override
   public User findByUsername(String username) {
      return userRepository.findByUsername(username).orElseThrow(
            () -> new NoSuchElementException("Not found username :: " + username));
   }

   @Override
   public User create(RegisterDTO registerDTO, Role role) {
      User user = new User(
            registerDTO.getFullName(),
            registerDTO.getUsername(),
            registerDTO.getEmail(),
            registerDTO.getPhone());
      user.setPassword(BCrypt.hashpw(registerDTO.getPassword(), BCrypt.gensalt(10)));
      user.getRoles().add(role);
      return user;
   }
}
