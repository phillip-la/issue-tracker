
package com.example.issuetracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueRepository issueRepository;

    public IssueController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @PostMapping
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue) {
        Issue savedIssue = issueRepository.save(issue);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedIssue);
    }

    @GetMapping
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        issueRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssue(@PathVariable Long id, @RequestBody Issue updatedIssue) {
        return issueRepository.findById(id)
            .map(existingIssue -> {
                existingIssue.setTitle(updatedIssue.getTitle());
                existingIssue.setDescription(updatedIssue.getDescription());
                existingIssue.setPriority(updatedIssue.getPriority());
                existingIssue.setOwner(updatedIssue.getOwner());
                existingIssue.setStartDate(updatedIssue.getStartDate());
                existingIssue.setEndDate(updatedIssue.getEndDate());

                Issue savedIssue = issueRepository.save(existingIssue);

                return ResponseEntity.ok(savedIssue);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

