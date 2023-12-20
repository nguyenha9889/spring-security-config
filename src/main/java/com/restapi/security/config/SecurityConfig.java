package com.restapi.security.config;

import com.restapi.security.jwt.JwtAuthTokenFilter;
import com.restapi.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity(jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

   private final UserDetailsService userDetailsService;
   private final JwtUtils jwtUtils;
   private final AuthEntryPointJwt authEntryPointJwt; // xử lý exception
   private final JwtAuthTokenFilter jwtAuthTokenFilter;
   private final PasswordEncoder passwordEncoder;
   @Bean
   public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
   }
   @Bean
   public AuthenticationProvider authProvider(){
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder);
      return authProvider;
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling( e -> e.authenticationEntryPoint(authEntryPointJwt))
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->
               auth.requestMatchers("/api/v1/public/**").permitAll()
                     .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                     .requestMatchers("/api/v1/pm/**").hasAnyRole("ADMIN","PM")
                     .requestMatchers("/api/v1/user/**").hasAnyRole("ADMIN","PM", "USER"));
      http.authenticationProvider(authProvider());
      http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
      return http.build();
   }
}
