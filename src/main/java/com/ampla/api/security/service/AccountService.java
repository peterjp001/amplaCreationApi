package com.ampla.api.security.service;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.security.entities.Role;
import com.ampla.api.security.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    User addNewUser(User appuser) throws DataAlreadyExistException, DataNotFoundException;

    User updateUser(Long id, User user) throws DataNotFoundException, DataAlreadyExistException;

    Optional<User> getUserById(Long id) throws DataNotFoundException;

    void deleteUser(Long id);

//    User addRoleUser(User user);

    void addNewRole(Role role);


    void addRoleToUser(String username, String roleName);
    void removeRoleToUSer(String username, String roleName);

    User getUserByusername(String username);

    List<User> listUsers();

    List<Role> listRoles();

    void refreshToken(HttpServletResponse res, HttpServletRequest req) throws Exception;

}
