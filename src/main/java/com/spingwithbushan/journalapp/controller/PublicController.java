package com.spingwithbushan.journalapp.controller;

import com.spingwithbushan.journalapp.entity.User;
import com.spingwithbushan.journalapp.service.Userservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    private final Userservice userService;

    @Autowired
    public PublicController(Userservice userService) {
        this.userService = userService;
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            userService.saveUserEntry(user);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            log.error("Error Occur while Creating User");
//            log.error("Error Occur while Creating User");
//            log.error("Error Occur while Creating User");
//            log.error("Error Occur while Creating User");
//            log.error("Error Occur while Creating User");
        }
        return ResponseEntity.badRequest().build();
    }
}
