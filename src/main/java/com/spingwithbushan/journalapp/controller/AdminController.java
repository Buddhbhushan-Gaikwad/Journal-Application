package com.spingwithbushan.journalapp.controller;


import com.spingwithbushan.journalapp.entity.User;
import com.spingwithbushan.journalapp.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    Userservice userservice;

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> all = userservice.getAll();
        if(!all.isEmpty()) {
            return ResponseEntity.ok(all);
        }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-users-admin")
    public ResponseEntity<String> createAdmin(@RequestBody User newUser) {
       boolean flage =  userservice.saveAdmin(newUser);
       if(flage) {
           return new ResponseEntity<>("Admin Successfully Created",HttpStatus.CREATED);
       }
       else{
           return new ResponseEntity<>("Admin Successfully Created",HttpStatus.BAD_REQUEST);
       }
    }
}
