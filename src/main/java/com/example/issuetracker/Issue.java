package com.example.issuetracker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity 
public class Issue {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String priority;
    private String owner;
    private LocalDate startDate;
    private LocalDate endDate;

    protected Issue() {

    }

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

    public Long getId() {
        return id;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
