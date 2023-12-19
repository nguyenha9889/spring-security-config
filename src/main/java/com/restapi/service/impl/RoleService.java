package com.restapi.service.impl;

import com.restapi.entity.Role;
import com.restapi.entity.RoleName;
import com.restapi.exception.NoSuchElementException;
import com.restapi.repository.IRoleRepository;
import com.restapi.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
   private final IRoleRepository roleRepository;

   @Override
   public Role findByRoleName(RoleName roleName) {
      return roleRepository.findByRoleName(roleName).orElseThrow(
            () -> new NoSuchElementException("Not found roleName :: " + roleName)
      );
   }
}
