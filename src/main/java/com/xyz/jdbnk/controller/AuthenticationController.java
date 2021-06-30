package com.xyz.jdbnk.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class AuthenticationController {

    @GetMapping("/authenticate")
    public String authenticate(@RequestHeader("Authorization") String authHeader) {
        String user=getUser(authHeader);
        String role= SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        Map<String, String> auth=new HashMap<>();
        auth.put("token", generateJwt(user,role));
        auth.put("role", role);
        String x = generateJwt(user, role);
        return x+" "+role;
    }

    public String getUser(String authHeader) {
        byte[] auth= Base64.getDecoder().decode(authHeader.split(" ")[1]);
        return new String(auth).split(":")[0];
    }

    public String generateJwt(String user, String role) {
        return Jwts.builder()
                .setSubject(user)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+1200000))
                .signWith(SignatureAlgorithm.HS256, "secretkey")
                .compact();
    }
}
