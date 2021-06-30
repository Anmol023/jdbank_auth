package com.xyz.jdbnk.controller;

import com.xyz.jdbnk.exception.AwsSesClientException;
import com.xyz.jdbnk.model.EmailRequestDto;
import com.xyz.jdbnk.services.AwsSesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/email")
public class EmailController {

    private final AwsSesService awsSesService;

    @Autowired
    public EmailController(AwsSesService awsSesService) {
        this.awsSesService = awsSesService;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDto emailRequestDto) {
        try {
            awsSesService.sendEmail(emailRequestDto.getEmail(), emailRequestDto.getBody());
            return ResponseEntity.ok("Successfully Sent Email");
        } catch (AwsSesClientException e) {
            return ResponseEntity.status(500).body("Error occurred while sending email " + e);
        }
    }
}