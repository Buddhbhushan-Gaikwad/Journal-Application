package com.spingwithbushan.journalapp.controller;

import com.spingwithbushan.journalapp.entity.User;
import com.spingwithbushan.journalapp.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Userservice userService;

    @GetMapping
    public List<User> getAllUsers() {
        try {
            return userService.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User userInDb = userService.findByUserName(currentUsername);
        if (userInDb == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        // Allow username change if provided
        if (user.getUserName() != null && !user.getUserName().isBlank()) {
            userInDb.setUserName(user.getUserName());
        }
        // Update password if provided
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            userInDb.setPassword(user.getPassword());
        }
        // Save without touching roles
        userService.saveUserEntry(userInDb);
        return ResponseEntity.ok("User updated successfully");
    }


    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            User userInDb = userService.findByUserName(currentUsername);
            if (userInDb.getUserName().equals(currentUsername)) {
                userService.deleteUser(userInDb);
            }
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            throw new RuntimeException("User Not Found: " + e.getMessage(), e);
        }
    }
}
