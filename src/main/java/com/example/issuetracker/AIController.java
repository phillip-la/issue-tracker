package com.example.issuetracker;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "https://nice-field-0a0d23900.3.azurestaticapps.net")
@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final OpenAIService openAIService;

    public AIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/suggest-priority")
    public PrioritySuggestion suggestPriority(@RequestBody Map<String, String> request) {

        String title = request.get("title");
        String description = request.get("description");

        return openAIService.suggestPriority(title, description);
    }
}