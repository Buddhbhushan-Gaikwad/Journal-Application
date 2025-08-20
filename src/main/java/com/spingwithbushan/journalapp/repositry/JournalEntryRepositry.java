package com.spingwithbushan.journalapp.repositry;

import com.spingwithbushan.journalapp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JournalEntryRepositry extends MongoRepository<JournalEntry, ObjectId> {
    // No need to define findAll() — it's inherited
}
