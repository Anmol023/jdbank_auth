package com.xyz.jdbnk.services;

import com.xyz.jdbnk.model.Role;
import com.xyz.jdbnk.model.User;
import com.xyz.jdbnk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class Dummy implements CommandLineRunner  {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findAll().isEmpty()){
            User admin = new User();
            admin.setEmail("admin@admin.com");
            admin.setPassword("password");
            admin.setName("Admin");
            admin.setRole(Role.ADMIN);
            userService.signup(admin);

            User inscmpny = new User();
            inscmpny.setEmail("max@max.com");
            inscmpny.setPassword("password");
            inscmpny.setName("Max Life");
            inscmpny.setRole(Role.INSCMPNY);
            inscmpny.setInsurer("MAX");
            userService.signup(inscmpny);

            User inscmpny1 = new User();
            inscmpny1.setEmail("sbi@sbi.com");
            inscmpny1.setPassword("password");
            inscmpny1.setName("State Bank");
            inscmpny1.setRole(Role.INSCMPNY);
            inscmpny1.setInsurer("SBI");
            userService.signup(inscmpny1);
        }
    }
}
