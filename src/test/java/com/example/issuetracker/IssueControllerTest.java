
package com.example.issuetracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
}

