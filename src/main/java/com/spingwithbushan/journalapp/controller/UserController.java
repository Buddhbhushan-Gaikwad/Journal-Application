package com.spingwithbushan.journalapp.controller;

import com.spingwithbushan.journalapp.entity.JournalEntry;
import com.spingwithbushan.journalapp.repositry.JournalEntryRepositry;
import com.spingwithbushan.journalapp.service.JournalEntryservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    private final Map<String, JournalEntry> journalEntries = new HashMap<>();

    @Autowired
    private JournalEntryservice journalEntryservice;
    @Autowired
    private JournalEntryRepositry journalEntryRepositry;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {
        List<JournalEntry> journalEntries = journalEntryRepositry.findAll();

        if (journalEntries != null && !journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> creatEntry(@RequestBody JournalEntry myEntry) {
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryservice.saveJournalEntry( myEntry);
            return new ResponseEntity<JournalEntry>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<JournalEntry>(myEntry, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryRepositry.findById(id);
        if(journalEntry.isPresent()) {
            return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<JournalEntry> deleteJournalEntryById(@PathVariable ObjectId id) {
        journalEntryservice.deleteJournalEntryById(id);
        return new ResponseEntity<JournalEntry>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryRepositry.findById(id).orElse(null);
        if(old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
            journalEntryservice.saveJournalEntry(old);
            return new ResponseEntity<JournalEntry>(old , HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

}
