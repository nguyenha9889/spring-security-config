package com.restapi.repository;

import com.restapi.model.Role;
import com.restapi.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
   Optional<Role> findByRoleName(RoleName roleName);
}
