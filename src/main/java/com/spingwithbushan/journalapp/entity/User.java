package com.spingwithbushan.journalapp.entity;

import com.spingwithbushan.journalapp.repositry.UserRepositry;
import com.spingwithbushan.journalapp.service.JournalEntryservice;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "user")
@Data
@Builder
public class User {
    @Id
    private ObjectId id;
    @NonNull
    @Indexed(unique = true)
    private String userName;
    @NonNull
    @Indexed(unique = true)
    private String password;

    private List<String> roles;

    // Reference the JournalEntry list as embedded
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
}

