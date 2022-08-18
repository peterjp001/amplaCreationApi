package com.ampla.api.security.service;

import com.ampla.api.security.entities.AppRole;
import com.ampla.api.security.entities.AppUser;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    AppUser addNewUser(AppUser appuser);

    Optional<AppUser> getUserById(Long id);

    AppUser addRoleUser(AppUser appUser);

    AppRole addNewRole(AppRole appRole);

    void addRoleToUser(String username, String roleName);

    AppUser getUserByusername(String username);

    List<AppUser> listUsers();

    List<AppRole> listRoles();

}
