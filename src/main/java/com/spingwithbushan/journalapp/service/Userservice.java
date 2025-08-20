package com.spingwithbushan.journalapp.service;

import com.spingwithbushan.journalapp.entity.JournalEntry;
import com.spingwithbushan.journalapp.repositry.JournalEntryRepositry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class JournalEntryservice {

    @Autowired
    private JournalEntryRepositry journalEntryRepositry;

    public void saveJournalEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepositry.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepositry.findAll();
    }
    public Optional<?> getJournalEntryById(ObjectId id) {
        return journalEntryRepositry.findById(id);

    }

    public boolean deleteJournalEntryById(ObjectId id) {
        journalEntryRepositry.deleteById(id);
        return true;
    }

    public boolean updateJournalEntryById(ObjectId id, JournalEntry journalEntry) {
        journalEntryRepositry.save(journalEntry);
        return true;
    }

}
