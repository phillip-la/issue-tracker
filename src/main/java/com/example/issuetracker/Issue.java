package com.example.issuetracker;

import java.time.LocalDate;

public class Issue {
    
    private String title;
    private String description;
    private String priority;
    private String owner;
    private LocalDate startDate;
    private LocalDate endDate;

    public Issue(
        String title,
        String description,
        String priority,
        String owner,
        LocalDate startDate,
        LocalDate endDate
    ) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.owner = owner;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getOwner() {
        return owner;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
