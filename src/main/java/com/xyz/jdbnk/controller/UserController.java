package com.xyz.jdbnk.controller;

import com.xyz.jdbnk.exception.InvalidLicense;
import com.xyz.jdbnk.model.Password;
import com.xyz.jdbnk.model.Policy;
import com.xyz.jdbnk.model.TempUser;
import com.xyz.jdbnk.model.User;
import com.xyz.jdbnk.services.AmazonClient;
import com.xyz.jdbnk.services.TempUserService;
import com.xyz.jdbnk.services.UserService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {
    private AmazonClient amazonClient;

    @Autowired
    private TempUserService service;

    @Autowired
    private UserService service1;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    private Policy getPolicy(String policyId){
        Policy policy =restTemplate.getForObject("https://22qorolith.execute-api.us-east-1.amazonaws.com/jdbnk/policy/id/"+policyId,Policy.class);
        System.out.println(policy);
        return policy;
    }
    @PostMapping("/signup")
    public TempUser signup(@RequestBody TempUser user) throws IOException, InvalidFormatException, InvalidLicense {
        return service.signup(user);
    }
    @GetMapping("/{email}")
    public User getCustomer(@PathVariable String email) {
        return service1.getUser(email);
    }

    @GetMapping("/check/{email}")
    public boolean checkUserId(@PathVariable String email) {
        if (!service.checkEmail(email))
            return true;
        return service1.checkEmail(email);
    }

    @GetMapping("/")
    public List<User> getAll(){
        return service1.getAllAgent();
    }

    @PutMapping("/addPolicy/{email}/{policyId}/{customerId}/{customerName}/{url}")
    public User addToSales(@PathVariable("email")String email,@PathVariable("policyId")String policyId,@PathVariable("customerId")String customerId,@PathVariable("customerName")String customerName,@PathVariable("url")String url){
        return service1.addToSales(email, getPolicy(policyId), customerId, customerName, url);
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @PutMapping("/deleteSold/{email}/{id}")
    public void deleteSold(@PathVariable("email")String email, @PathVariable("id")String id){
        service1.deleteSoldItem(email, id);
    }

    @PutMapping("/user/{email}")
    public boolean updatePassword(@PathVariable String email, @RequestBody Password password) {
        User user=service1.getUser(email);
        if(passwordEncoder.matches(password.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(password.getNewPassword()));
            service1.updateUser(user);
            return true;
        }
        return false;
    }
    @GetMapping({ "/getSold/{email}" })
    public List getSoldItems(@PathVariable final String email) {
        final User user = this.service1.getUser(email);
        return this.service1.getSold(user);
    }

    @GetMapping({ "/signed/{id}" })
    public String seeFile(@PathVariable final String id) {
        return this.amazonClient.getSigned(id);
    }

}
