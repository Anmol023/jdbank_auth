package com.xyz.jdbnk.controller;


import com.xyz.jdbnk.model.Role;
import com.xyz.jdbnk.model.TempUser;
import com.xyz.jdbnk.model.User;
import com.xyz.jdbnk.repository.TempUserRepository;
import com.xyz.jdbnk.repository.UserRepository;
import com.xyz.jdbnk.services.TempUserService;
import com.xyz.jdbnk.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/admin")
@RestController
public class Admin {

    @Autowired
    TempUserRepository tempUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TempUserService tempUserService;

    @Autowired
    UserService service;

    @PostMapping("/signup")
    public User signup(@RequestBody User user){
        user.setRole(Role.ADMIN);
        user.setPassword("password");
        return userRepository.save(user);
    }

    @GetMapping("/allUnverifiedAgent")
    public List<TempUser> allUnverifiedUser(){
        return tempUserRepository.findAll();
    }

    @GetMapping("/allVerifiedAgent")
    public List<User> allVerifiedUser(){
        return userRepository.findAllAgent();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity toDelete(@PathVariable(value = "email")String email){
        tempUserService.delete(email);
        return ResponseEntity.ok("Deletion Successful");
    }

    @GetMapping(value = "/verify/{email}")
    public ResponseEntity<String> toVerify(@PathVariable(value = "email") String email){
        User user = service.verify(email);
        return ResponseEntity.ok("Verified");
    }
}
