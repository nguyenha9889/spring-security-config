package com.restapi.service;

import com.restapi.entity.Role;
import com.restapi.entity.RoleName;

public interface IRoleService {
   Role findByRoleName(RoleName roleName);
}
