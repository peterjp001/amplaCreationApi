package com.ampla.api.security.repository;

import com.ampla.api.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(String rolename);
}
