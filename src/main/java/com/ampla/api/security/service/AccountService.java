package com.ampla.api.security.service;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.exception.UserAlreadyExistException;
import com.ampla.api.security.entities.Role;
import com.ampla.api.security.entities.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    User addNewUser(User appuser) throws UserAlreadyExistException;

    Boolean isUserHasRoles(String element, Collection<Role> role1);

    User updateUser(Long id, User user) throws DataNotFoundException, UserAlreadyExistException;

    Optional<User> getUserById(Long id) throws DataNotFoundException;

    void deleteUser(Long id);

    User addRoleUser(User user);

    Role addNewRole(Role role);


    void addRoleToUser(String username, String roleName);
    void removeRoleToUSer(String username, String roleName);

    User getUserByusername(String username);

    List<User> listUsers();

    List<Role> listRoles();

}
