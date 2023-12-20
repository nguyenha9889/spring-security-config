package com.restapi.service;

import com.restapi.model.Role;

public interface IRoleService {
   Role findByRoleName(RoleName roleName);
}
