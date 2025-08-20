package com.spingwithbushan.journalapp.service;

import com.spingwithbushan.journalapp.entity.User;
import com.spingwithbushan.journalapp.repositry.UserRepositry;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class Userservice {

    @Autowired
    private UserRepositry userRepositry;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepositry.findAll();
    }

    public boolean saveUserEntry(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepositry.save(user);
            return true;
        } catch (Exception e) {
            log.error("Error Occur while Creating User");
            log.debug("Error Occur while Creating User");
            log.warn("Error Occur while Creating User");
            log.info("Error Occur while Creating User");
            log.trace("Error Occur while Creating User");
            return false;
        }
    }

    public void saveEntry(User user) {
        user.setRoles(Arrays.asList("USER"));
        userRepositry.save(user);
    }

    public Optional<User> finedById(ObjectId id) {
        return userRepositry.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepositry.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepositry.findByUserName(userName);
    }

    public boolean saveAdmin(User user) {
        boolean flage  = true;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepositry.save(user);

        return flage;
    }


    public void deleteUser(User userInDb) {
        userRepositry.delete(userInDb);
    }
}
