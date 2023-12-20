package com.restapi.service.impl;

import com.restapi.model.Role;
import com.restapi.exception.NoSuchElementException;
import com.restapi.repository.IRoleRepository;
import com.restapi.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
   private final IRoleRepository roleRepository;
}
