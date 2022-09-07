package com.ampla.api.security.service;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.exception.UserAlreadyExistException;
import com.ampla.api.security.entities.Role;
import com.ampla.api.security.entities.User;
import com.ampla.api.security.repository.RoleRepository;
import com.ampla.api.security.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    protected static final Log logger = LogFactory.getLog(AccountServiceImpl.class);
    private final UserRepository appUserRepository;
    private final RoleRepository appRoleRepository;

    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(UserRepository appUserRepository, RoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addNewUser(User appuser) throws UserAlreadyExistException, DataNotFoundException {
        User newUser = new User();
        if (appUserRepository.findByUsername(appuser.getUsername()) != null){
            throw new UserAlreadyExistException("User "+ appuser.getUsername()+" Already exist");
        }

        newUser.setUsername(appuser.getUsername());
        if (!appuser.getPassword().isEmpty()){
            newUser.setPassword(passwordEncoder.encode(appuser.getPassword()));
        }
        newUser = appUserRepository.save(newUser);
        if(newUser == null){
            throw new UserAlreadyExistException("User "+ appuser.getUsername()+" Already exist");
        }

        if (appuser.getRoles().size() >0){
            User finalNewUser = newUser;
            appuser.getRoles().forEach(role -> {
                addRoleToUser(finalNewUser.getUsername(), role.getRoleName());
            });
        }else{
            throw new DataNotFoundException("Role can't be blank");
        }
        return newUser;
    }



    @Override
    public User updateUser(Long id, User user) throws DataNotFoundException, UserAlreadyExistException {
        Optional<User> uniqueUser = appUserRepository.findById(id);

        User updatedUser = new User();
        if (uniqueUser.isPresent()) {
            User currentUser = uniqueUser.get();

            String username = user.getUsername();
            String password = user.getPassword();
            Collection<Role> roles = user.getRoles();

            if (username != null) {
                User testUser = appUserRepository.findByUsername(username);
                if( testUser != null){
                    if(currentUser.getId() != testUser.getId()){
                        throw new UserAlreadyExistException("User "+ username+" Already exist");
                    }
                }
                currentUser.setUsername(username);
            }

            if (password != null) {
                currentUser.setPassword(password);
            }

            if (roles.size() > 0) {
                roles.forEach(role -> {
                  addRoleToUser(currentUser.getUsername(), role.getRoleName());
                });

            }else{
                logger.info("No role to update");
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


//    @Override
//    public User addRoleUser(User user) {
//        return appUserRepository.save(user);
//    }


    @Override
    public Role addNewRole(Role role) {
        return appRoleRepository.save(role);
    }



    @Override
    public void addRoleToUser(String username, String roleName) {
        User au = appUserRepository.findByUsername(username);
        Role ar = appRoleRepository.findByRoleName(roleName);
        if(!au.getRoles().contains(ar)){
            logger.info("Role "+ar.getRoleName()+ " added To "+ au.getUsername());
            au.getRoles().add(ar);
        }else{
            logger.error("Can't add Role "+ar.getRoleName()+ " To "+ au.getUsername());
        }
    }

    @Override
    public void removeRoleToUSer(String username, String roleName) {
        User au = appUserRepository.findByUsername(username);
        Role ar = appRoleRepository.findByRoleName(roleName);


        if(!au.getRoles().contains(ar)){
            logger.warn("Remove Role "+ar.getRoleName()+ " To "+ au.getUsername());
            au.getRoles().remove(ar);
        }else{
            logger.error("Can't remove sRole "+ar.getRoleName()+ " To "+ au.getUsername());
        }
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

    @Override
    public void refreshToken(HttpServletResponse res, HttpServletRequest req) throws Exception{
        String jwtAuthorizationToken = req.getHeader("Authorization");
        if(jwtAuthorizationToken != null && jwtAuthorizationToken.startsWith("Bearer ")){

            try{
                logger.info("Get new Token");
                String jwt = jwtAuthorizationToken.substring(7);
                Algorithm algo = Algorithm.HMAC256("noby");
                JWTVerifier jwtVerifier = JWT.require(algo).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                User user = appUserRepository.findByUsername(username);

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
                logger.error("Block new Refresh Token");
            }
        }else{
            throw  new RuntimeException("Refresh Token Required");
        }
    }

}
