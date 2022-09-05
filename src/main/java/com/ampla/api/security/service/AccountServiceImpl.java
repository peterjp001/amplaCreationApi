package com.ampla.api.security.service;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.security.entities.Role;
import com.ampla.api.security.entities.User;
import com.ampla.api.security.repository.RoleRepository;
import com.ampla.api.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final UserRepository appUserRepository;
    private final RoleRepository appRoleRepository;

    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(UserRepository appUserRepository, RoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addNewUser(User appuser) {
        User newUser = new User();

        newUser.setUsername(appuser.getUsername());
        if (!appuser.getPassword().isEmpty()){
            newUser.setPassword(passwordEncoder.encode(appuser.getPassword()));
        }
        newUser = appUserRepository.save(newUser);

        if (appuser.getRoles().size() >0){
            User finalNewUser = newUser;
            appuser.getRoles().forEach(role -> {
                addRoleToUser(finalNewUser.getUsername(), role.getRoleName());
            });
        }
        return newUser;
    }

    @Override
    public Boolean isUserHasRoles(String element, Collection<Role> role) {
        boolean statement = false;
        if (role.size()>0){
            for (Role userRole : role){
                statement = Objects.equals(element, userRole.getRoleName()) ;
            }
        }

        return statement;
    }

    @Override
    public User updateUser(Long id, User user) throws DataNotFoundException {
        Optional<User> uniqueUser = appUserRepository.findById(id);
        User updatedUser = new User();
        if (uniqueUser.isPresent()) {
            User currentUser = uniqueUser.get();

            String username = user.getUsername();
            String password = user.getPassword();
            Collection<Role> roles = user.getRoles();

            if (username != null) {
                currentUser.setUsername(username);
            }

            if (password != null) {
                currentUser.setPassword(password);
            }

            if (roles.size() > 0) {
                roles.forEach(role -> {
                    if(!isUserHasRoles(role.getRoleName(), currentUser.getRoles())){
                        addRoleToUser(currentUser.getUsername(),role.getRoleName());
                    }else{
                        System.out.println("false");
                    }
                });
            }

            updatedUser = appUserRepository.save(currentUser);
        }else{
            throw new DataNotFoundException("User with id "+ id + " doesn't exist");
        }
        return updatedUser;
    }

    @Override
    public Optional<User> getUserById(Long id) throws DataNotFoundException  {
        Optional<User> u = appUserRepository.findById(id);
        if(u.isEmpty()) {
            throw new DataNotFoundException("User with id "+ id + " doesn't exist");
        }
        return u;
    }

    @Override
    public void deleteUser(Long id) {
        appUserRepository.deleteById(id);
    }


    @Override
    public User addRoleUser(User user) {
        return appUserRepository.save(user);
    }


    @Override
    public Role addNewRole(Role role) {
        return appRoleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User au = appUserRepository.findByUsername(username);
        Role ar = appRoleRepository.findByRoleName(roleName);
        System.out.println("Role "+ar.getRoleName()+ " To "+ au.getUsername());

        au.getRoles().add(ar);
    }

    @Override
    public User getUserByusername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<User> listUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public List<Role> listRoles() {
        return appRoleRepository.findAll();
    }
}
