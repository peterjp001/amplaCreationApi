package com.ampla.api.security.service;

import com.ampla.api.security.entities.AppRole;
import com.ampla.api.security.entities.AppUser;
import com.ampla.api.security.repository.AppRoleRepository;
import com.ampla.api.security.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    private PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser addNewUser(AppUser appuser) {
        String password = appuser.getPassword();
        appuser.setPassword(passwordEncoder.encode(password));
        return appUserRepository.save(appuser);
    }

    @Override
    public AppUser addRoleUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser au = appUserRepository.findByUsername(username);
        AppRole ar = appRoleRepository.findByRoleName(roleName);
        au.getAppRoles().add(ar);

    }

    @Override
    public AppUser getUserByusername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }
}
