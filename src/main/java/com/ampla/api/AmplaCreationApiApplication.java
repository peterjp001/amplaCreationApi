package com.ampla.api;

import com.ampla.api.security.entities.Role;
import com.ampla.api.security.entities.User;
import com.ampla.api.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
            Optional<User> appUser = Optional.ofNullable(accountService.getUserByusername("JohnDoe"));
            if(appUser.isEmpty()){
                accountService.addNewRole(new Role(null,"ADMIN"));
                accountService.addNewRole(new Role(null,"USER"));

                User superAdmin = new User();
                superAdmin.setUsername("JohnDoe");
                superAdmin.setPassword("Admin@123");
                List<Role> superAdminRole = new ArrayList<>();
                superAdminRole.add(new Role(null,"ADMIN"));
                superAdminRole.add(new Role(null,"USER"));
                superAdmin.setRoles(superAdminRole);
                accountService.addNewUser(superAdmin);
                System.out.println("super administrator roles assigned to the user");
            }else{
                System.out.println("Super Admin Exist");
                System.out.println(new Date(System.currentTimeMillis()+120*60*1000));
            }


        };
    }

}
