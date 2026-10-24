
package com.example.issuetracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.http.MediaType;

import tools.jackson.databind.JsonNode;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(
    statements = "DELETE FROM issue",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class IssueControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void shouldCreateIssue() {
        RestClient client = RestClient.create("http://localhost:" + port);

        String requestBody = """
            {
                "title": "Login page error",
                "description": "Users cannot log in",
                "priority": "HIGH",
                "owner": "Phillip",
                "startDate": "2026-10-24",
                "endDate": "2026-10-30"
            }
            """;

        ResponseEntity<String> response = client.post()
            .uri("/api/issues")
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .body(requestBody)
            .retrieve()
            .toEntity(String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("\"id\":"));
        assertTrue(response.getBody().contains("\"title\":\"Login page error\""));
    }

    @Test
    void shouldGetAllIssues() {
        RestClient client = RestClient.create("http://localhost:" + port);

        String requestBody = """
                {
                    "title": "GET endpoint test issue",
                    "description": "Verify issue retrieval",
                    "priority": "HIGH",
                    "owner": "Phillip",
                    "startDate": "2026-10-24",
                    "endDate": "2026-10-30"
                }
                """;

        ResponseEntity<String> created = client.post()
                .uri("/api/issues")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);

        assertEquals(HttpStatus.CREATED, created.getStatusCode());

        ResponseEntity<String> response = client.get()
                .uri("/api/issues")
                .retrieve()
                .toEntity(String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().startsWith("["));
        assertTrue(response.getBody().contains("\"title\":\"GET endpoint test issue\""));
    }

    @Test
    void shouldDeleteIssue() throws Exception {
        RestClient client = RestClient.create("http://localhost:" + port);

        String requestBody = """
                {
                    "title": "Issue to delete",
                    "description": "Testing the DELETE endpoint",
                    "priority": "LOW",
                    "owner": "Phillip",
                    "startDate": "2026-10-24",
                    "endDate": "2026-10-30"
                }
                """;

        ResponseEntity<String> created = client.post()
                .uri("/api/issues")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);

        assertEquals(HttpStatus.CREATED, created.getStatusCode());
        assertNotNull(created.getBody());

        JsonNode issue = new ObjectMapper().readTree(created.getBody());
        long id = issue.get("id").asLong();

        ResponseEntity<Void> deleted = client.delete()
                .uri("/api/issues/{id}", id)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, deleted.getStatusCode());

        ResponseEntity<String> response = client.get()
            .uri("/api/issues")
            .retrieve()
            .toEntity(String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        JsonNode issues = new ObjectMapper().readTree(response.getBody());

        for (JsonNode issueNode : issues)
            assertNotEquals(id, issueNode.get("id").asLong());

    }
}

