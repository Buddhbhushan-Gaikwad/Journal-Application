package com.spingwithbushan.journalapp.service;

import com.spingwithbushan.journalapp.entity.User;
import com.spingwithbushan.journalapp.repositry.UserRepositry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
public class UserserviceTests {

    @Autowired
    private Userservice userservice;

    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void TestSaveNewUser(User user){
        assertTrue(userservice.saveUserEntry(user));
    }
}
