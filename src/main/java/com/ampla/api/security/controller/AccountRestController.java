package com.ampla.api.security.controller;

import com.ampla.api.exception.DataNotFoundException;
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
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(accountService.addNewUser(user));
    }

    @PutMapping(path="/user/{id}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) throws DataNotFoundException {
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


//    @PostMapping(path="/addRoleToUser")
//    @PostAuthorize("hasAnyAuthority('ADMIN')")
//    public void addRoleToUser(@RequestBody RoleUserDTO roleUserDTO){
//        accountService.addRoleToUser(roleUserDTO.getUsername(), roleUserDTO.getRoleName());
//    }

    @GetMapping(path="/refreshToken")
    public void refreshToken(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String jwtAuthorizationToken = req.getHeader("Authorization");
        if(jwtAuthorizationToken != null && jwtAuthorizationToken.startsWith("Bearer ")){

            try{
                System.out.println("Get new Token");
                String jwt = jwtAuthorizationToken.substring(7);
                Algorithm algo = Algorithm.HMAC256("noby");
                JWTVerifier jwtVerifier = JWT.require(algo).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                User user = accountService.getUserByusername(username);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+120*60*1000))
                        .withIssuer(req.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
                        .sign(algo);

                Map<String,String> idToken = new HashMap<>();
                idToken.put("access_token", access_token);
                idToken.put("refresh_token", jwt);
                new ObjectMapper().writeValue(res.getOutputStream(),idToken);
                res.setContentType("application/json");


            }catch (Exception e){
                res.setHeader("error-message",e.getMessage());
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                System.out.println("Block new Refresh Token");
            }
        }else{
            throw  new RuntimeException("Refresh Token Required");
        }
    }

    @GetMapping(path ="/profile")
        public User profile(Principal principal){
            return accountService.getUserByusername(principal.getName());
        }

}

