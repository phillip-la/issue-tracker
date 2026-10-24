package com.example.issuetracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.transaction.annotation.Transactional;

@SpringBootTest 
@Transactional 
public class IssueRepositoryTest {
    
    @Autowired 
    private IssueRepository issueRepository;

    @Test 
    void shouldSaveAndRetrieveIssue() {
        Issue issue = new Issue(
            "Login page error",
            "Users cannot log in",
            "HIGH",
            "Phillip",
            LocalDate.of(2026, 10, 24),
            LocalDate.of(2026, 10, 30)
        );

        Issue savedIssue = issueRepository.save(issue);

        assertNotNull(savedIssue.getId());

        Issue retrievedIssue = issueRepository
            .findById(savedIssue.getId())
            .orElseThrow();

        assertEquals("Login page error", retrievedIssue.getTitle());
        assertEquals("Phillip", retrievedIssue.getOwner());
    }
}
