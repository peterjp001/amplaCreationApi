package com.ampla.api.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ampla.api.security.entities.AppRole;
import com.ampla.api.security.entities.AppUser;
import com.ampla.api.security.service.AccountService;
import lombok.Data;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountRestController {

    private AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping(path="/users")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<AppUser> appUsers(){
        return  accountService.listUsers();
    }

    @PostMapping(path="/users")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return accountService.addNewUser(appUser);
    }

    @PostMapping(path="/roles")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return accountService.addNewRole(appRole);
    }

    @PostMapping(path="/addRoleToUser")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
        accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
    }

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
                AppUser appUser = accountService.getUserByusername(username);

                String access_token = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                        .withIssuer(req.getRequestURL().toString())
                        .withClaim("roles", appUser.getAppRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
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
        public AppUser profile(Principal principal){
            return accountService.getUserByusername(principal.getName());
        }

}

@Data
class RoleUserForm
{
    private String username;
    private String roleName;
}