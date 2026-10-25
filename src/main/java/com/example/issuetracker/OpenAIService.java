package com.example.issuetracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service 
public class OpenAIService {
    private final RestClient restClient;

    public OpenAIService(@Value("${openai.api.key}") String apiKey) {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public PrioritySuggestion suggestPriority(String title, String description) {
        String prompt = """
            You are an issue prioritisation assistant.

            Classify the following software issue as LOW, MEDIUM, or HIGH.

            Guidelines:
            HIGH: Critical failures, security vulnerabilities,
                production outages, or major business/project disruption.
            MEDIUM: Significant problems that affect functionality
                    but do not cause critical disruption.
            LOW: Minor bugs, cosmetic issues, or small improvements.

            Return a JSON object containing exactly these fields:
            - priority: LOW, MEDIUM, or HIGH
            - reason: A detailed explanation of the recommendation, as well as,
            a detailed actionable plan that could be effective in mitigating the issue.
            Do not simply list actions, and write coherent sentences. No more than 150 words.

            Return only valid JSON, without Markdown formatting.

            Issue title: %s
            Issue description: %s
            """.formatted(title, description);

        Map<String, Object> requestBody = Map.of(
            "model", "gpt-6-luna",
            "input", prompt,
            "text", Map.of(
                "format", Map.of(
                    "type", "json_schema",
                    "name", "priority_suggestion",
                    "strict", true,
                    "schema", Map.of(
                        "type", "object",
                        "properties", Map.of(
                            "priority", Map.of(
                                "type", "string",
                                "enum", new String[]{"LOW", "MEDIUM", "HIGH"}
                            ),
                            "reason", Map.of(
                                "type", "string"
                            )
                        ),
                        "required", new String[]{"priority", "reason"},
                        "additionalProperties", false
                    )
                )
            )
        );

        String response = restClient.post()
                .uri("/responses")
                .body(requestBody)
                .retrieve()
                .body(String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response);

        for (JsonNode output : json.path("output")) {
            for (JsonNode content : output.path("content")) {
                if ("output_text".equals(content.path("type").asString())) {
                    String aiText = content.path("text").asString().trim();

                    JsonNode suggestion = mapper.readTree(aiText);

                    String priority = suggestion.path("priority").asString();
                    String reason = suggestion.path("reason").asString();

                    if (!priority.equals("LOW") &&
                        !priority.equals("MEDIUM") &&
                        !priority.equals("HIGH")) {
                        throw new IllegalStateException("Invalid AI priority: " + priority);
                    }

                    if (reason.isBlank()) {
                        throw new IllegalStateException("AI returned no explanation");
                    }

                    return new PrioritySuggestion(priority, reason);
                }
            }
        }

        throw new IllegalStateException("OpenAI returned no text response");
    }
}
