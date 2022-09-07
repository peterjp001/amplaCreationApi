package com.ampla.api.security.controller;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.exception.UserAlreadyExistException;
import com.ampla.api.mis.dto.RoleUserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ampla.api.security.entities.Role;
import com.ampla.api.security.entities.User;
import com.ampla.api.security.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountRestController {

    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping(path="/users")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public List<User> appUsers(){
        return  accountService.listUsers();
    }

    @GetMapping(path="/user/{id}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public Optional<User> appUser(@PathVariable Long id) throws DataNotFoundException {
        return  accountService.getUserById(id);
    }

    @PostMapping(path="/user")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) throws UserAlreadyExistException, DataNotFoundException {
        return ResponseEntity.ok(accountService.addNewUser(user));
    }

    @PutMapping(path="/user/{id}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) throws DataNotFoundException, UserAlreadyExistException {
            return ResponseEntity.ok( accountService.updateUser(id,user));
    }

    @DeleteMapping(path="/user/{id}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteUser(@PathVariable("id") Long id){
        accountService.deleteUser(id);
    }


//    @PostMapping(path="/roles")
//    @PostAuthorize("hasAnyAuthority('ADMIN')")
//    public Role saveRole(@RequestBody Role role){
//        return accountService.addNewRole(role);
//    }

    @GetMapping(path="/roles")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<Role> getAllRoles(){
        return accountService.listRoles();
    }



    @PostMapping(path="/addroletouser/user/{username}/role/{roleName}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void addRoleToUser(@PathVariable("username") String username, @PathVariable("roleName") String roleName){
        accountService.addRoleToUser(username, roleName);
    }

    @PostMapping(path="/removeroletouser/user/{username}/role/{roleName}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void removeRoleToUser(@PathVariable("username") String username, @PathVariable("roleName") String roleName){
        accountService.removeRoleToUSer(username, roleName);
    }

    @GetMapping(path="/refreshToken")
    public void refreshToken(HttpServletRequest req, HttpServletResponse res) throws Exception {
        accountService.refreshToken(res,req);
    }

    @GetMapping(path ="/profile")
        public User profile(Principal principal){
            return accountService.getUserByusername(principal.getName());
        }

}

