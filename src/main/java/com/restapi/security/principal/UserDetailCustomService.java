package com.restapi.security.principal;

import com.restapi.model.User;
import com.restapi.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailCustomService implements UserDetailsService {
   private final IUserRepository userRepository;
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUsernameOrEmail(username, username).orElseThrow(
            () -> new UsernameNotFoundException("Username not found"));

      List<GrantedAuthority> authorities = user.getRoles().stream().map(
            role -> new SimpleGrantedAuthority(role.getRoleName().name())
      ).collect(Collectors.toList());

      return UserDetailCustom.builder()
            .id(user.getId())
            .username(username)
            .email(user.getEmail())
            .password(user.getPassword())
            .authorities(authorities)
            .build();
   }
}
