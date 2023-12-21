package com.restapi.service;

import com.restapi.model.Role;
import com.restapi.model.RoleName;
import java.util.Optional;

public interface IRoleService extends IGenericService<Role, Long>{
   Optional<Role> findByRoleName(RoleName roleName);
}
