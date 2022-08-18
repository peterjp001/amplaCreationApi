package com.ampla.api;

import com.ampla.api.security.entities.AppRole;
import com.ampla.api.security.entities.AppUser;
import com.ampla.api.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AmplaCreationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmplaCreationApiApplication.class, args);
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(AccountService accountService){
        return args -> {
            Optional<AppUser> appUser = Optional.ofNullable(accountService.getUserByusername("JohnDoe"));
            if(appUser.isEmpty()){
                accountService.addNewUser(new AppUser(null,"JohnDoe","Admin@123",new ArrayList<>(), null));
                System.out.println("super administrator user created");
                accountService.addNewRole(new AppRole(null, "ADMIN"));
                accountService.addNewRole(new AppRole(null, "USER"));
                System.out.println("USER and ADMIN roles created");
                accountService.addRoleToUser("JohnDoe","USER");
                accountService.addRoleToUser("JohnDoe","ADMIN");
                System.out.println("super administrator roles assigned to the user");
            }else{
                System.out.println("Super Admin Exist");
            }
//            ADD ROLE TO THE DATABASE
//            accountService.addNewRole(new AppRole(null, "STANDARD"));
//            accountService.addNewRole(new AppRole(null, "ADMIN"));

//          ADD USER TO THE DATABASE
//            accountService.addNewUser(new AppUser(null,"user1","1234",new ArrayList<>()));
//            accountService.addNewUser(new AppUser(null,"user2","1234",new ArrayList<>()));

//          ADD ROLE TO USER
//            accountService.addRoleToUser("user1","ADMIN");
//            accountService.addRoleToUser("user1","STANDARD");
//            accountService.addRoleToUser("user2","STANDARD");


        };
    }

}
