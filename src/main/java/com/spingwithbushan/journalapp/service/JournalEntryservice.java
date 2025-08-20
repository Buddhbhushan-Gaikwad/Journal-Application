package com.spingwithbushan.journalapp.service;

import com.spingwithbushan.journalapp.entity.JournalEntry;
import com.spingwithbushan.journalapp.entity.User;
import com.spingwithbushan.journalapp.repositry.JournalEntryRepositry;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
@Slf4j
public class JournalEntryservice {

    @Autowired
    private JournalEntryRepositry journalEntryRepositry;
    @Autowired
    private Userservice userservice;

    @Transactional
    public void saveJournalEntry(JournalEntry journalEntry, String userName) {

        try {
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepositry.save(journalEntry);
            User user = userservice.findByUserName(userName);
            if (user.getJournalEntries() == null) {
                user.setJournalEntries(new ArrayList<>());
            }
            user.getJournalEntries().add(savedEntry);
            userservice.saveEntry(user);
        } catch (Exception e) {
            log.error("The Error occurs while Saving the journalEntry the entry");
            log.warn("The Error occurs while Saving the journalEntry the entry");
            log.info("The Error occurs while Saving the journalEntry the entry");
            log.trace("The Error occurs while Saving the journalEntry the entry");
            log.debug("The Error occurs while Saving the journalEntry the entry");
            throw new RuntimeException("The Error occurs while Saving the journalEntry the entry"+e);
        }
    }

    public void saveJournalEntry(JournalEntry journalEntry) {
        // 1. Set current timestamp
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepositry.save(journalEntry);

    }

    public List<JournalEntry> getAll() {
        return journalEntryRepositry.findAll();
    }
    public Optional<?> getJournalEntryById(ObjectId id) {
        return journalEntryRepositry.findById(id);

    }

    @Transactional
    public boolean deleteJournalEntryById(ObjectId id, String userName) {
        boolean flage = false;
        try {
            User user = userservice.findByUserName(userName);
            flage = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (flage) {
                journalEntryRepositry.deleteById(id);
                userservice.saveEntry(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("The Error occurs while deleting the entry"+e);
        }
        return flage;
    }

    public boolean updateJournalEntryById(ObjectId id, JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepositry.save(journalEntry);
        return true;
    }

}
