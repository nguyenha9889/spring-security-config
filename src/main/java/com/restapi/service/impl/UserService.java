package com.restapi.service.impl;

import com.restapi.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
   private final IUserRepository userRepository;


}
