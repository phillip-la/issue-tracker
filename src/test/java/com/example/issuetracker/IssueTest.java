package com.example.issuetracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class IssueTest {
    
    @Test 
    void shouldCreateIssueWithRequiredFields() {
        LocalDate startDate = LocalDate.of(2026, 10, 24);
        LocalDate endDate = LocalDate.of(2026, 10, 30);

        Issue issue = new Issue(
            "Login page error",
            "Users cannot log in",
            "HIGH",
            "Phillip",
            startDate,
            endDate
        );

        assertEquals("Login page error", issue.getTitle());
        assertEquals("Users cannot log in", issue.getDescription());
        assertEquals("HIGH", issue.getPriority());
        assertEquals("Phillip", issue.getOwner());
        assertEquals(startDate, issue.getStartDate());
        assertEquals(endDate, issue.getEndDate());
    }
}
