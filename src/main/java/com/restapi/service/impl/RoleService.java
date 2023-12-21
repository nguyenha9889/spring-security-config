package com.restapi.service.impl;

import com.restapi.model.Role;
import com.restapi.model.RoleName;
import com.restapi.repository.IRoleRepository;
import com.restapi.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
   private final IRoleRepository roleRepository;


   @Override
   public Optional<Role> findByRoleName(RoleName roleName) {
      return roleRepository.findByRoleName(roleName);
   }
}
