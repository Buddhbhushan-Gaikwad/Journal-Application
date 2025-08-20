package com.spingwithbushan.journalapp.controller;

import com.spingwithbushan.journalapp.entity.JournalEntry;
import com.spingwithbushan.journalapp.entity.User;
import com.spingwithbushan.journalapp.repositry.JournalEntryRepositry;
import com.spingwithbushan.journalapp.service.JournalEntryservice;
import com.spingwithbushan.journalapp.service.Userservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {


    @Autowired
    private JournalEntryservice journalEntryservice;
    @Autowired
    private JournalEntryRepositry journalEntryRepositry;
    @Autowired
    private Userservice userService;


    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User byUserName = userService.findByUserName(currentUsername);
        List<JournalEntry> journalEntries = byUserName.getJournalEntries();

        if (journalEntries != null && !journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        try {
            journalEntryservice.saveJournalEntry(myEntry, currentUsername);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.findByUserName(currentUsername);
        for (JournalEntry journalEntry : user.getJournalEntries()) {
            if(id.equals(journalEntry.getId())) {
                return new ResponseEntity<>(journalEntry, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<JournalEntry> deleteJournalEntryById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        try {
            boolean removed = journalEntryservice.deleteJournalEntryById(id, currentUsername);
            if(removed) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                return new ResponseEntity<JournalEntry>( HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<JournalEntry>( HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.findByUserName(currentUsername);
        for (JournalEntry journalEntry : user.getJournalEntries()) {
            if(id.equals(journalEntry.getId())) {
                journalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : journalEntry.getTitle());
                journalEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : journalEntry.getContent());
                journalEntryservice.updateJournalEntryById(id, journalEntry);
                return new ResponseEntity<>(journalEntry, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

}
