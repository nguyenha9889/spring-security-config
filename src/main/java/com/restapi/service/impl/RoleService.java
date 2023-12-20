package com.restapi.service.impl;

import com.restapi.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
   private final IRoleRepository roleRepository;
}
