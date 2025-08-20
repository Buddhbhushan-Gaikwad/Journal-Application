package com.spingwithbushan.journalapp.controller;

import com.spingwithbushan.journalapp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

    private final Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());

    }

    @PostMapping
    public boolean creatEntry(@RequestBody JournalEntry myEntry) {
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    @GetMapping("id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable long id) {
        return journalEntries.get(id);
    }

    @DeleteMapping("id/{id}")
    public boolean deleteJournalEntryById(@PathVariable long id) {
        journalEntries.remove(id);
        return true;
    }

    @PutMapping("id/{id}")
    public boolean updateJournalEntryById(@PathVariable long id, @RequestBody JournalEntry myEntry) {
        journalEntries.put(id, myEntry);
        return true;
    }
}
