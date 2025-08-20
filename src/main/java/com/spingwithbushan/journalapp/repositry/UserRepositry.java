package com.spingwithbushan.journalapp.repositry;

import com.spingwithbushan.journalapp.entity.JournalEntry;
import com.spingwithbushan.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepositry extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);

}
