package com.agriculture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgricultureApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void coreHttpFlowWorksWithSeedDataAndAuthorization() {
        String adminToken = login("admin", "123456");
        String farmerToken = login("farmer", "123456");

        ResponseEntity<Map> noToken = restTemplate.getForEntity("/api/user/info", Map.class);
        assertThat(noToken.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        ResponseEntity<Map> adminInfo = get("/api/user/info", adminToken);
        assertThat(adminInfo.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(adminInfo.getBody()).extracting("code").isEqualTo(200);
        Map<?, ?> admin = (Map<?, ?>) adminInfo.getBody().get("data");
        assertThat(admin.get("username")).isEqualTo("admin");
        assertThat(admin.containsKey("password")).isFalse();

        ResponseEntity<Map> categories = get("/api/crop/category/list", adminToken);
        assertThat(categories.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(categories.getBody()).extracting("code").isEqualTo(200);

        ResponseEntity<Map> stats = get("/api/stats/overview", adminToken);
        assertThat(stats.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(stats.getBody()).extracting("code").isEqualTo(200);

        ResponseEntity<Map> forbiddenStats = get("/api/stats/overview", farmerToken);
        assertThat(forbiddenStats.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(forbiddenStats.getBody()).extracting("code").isEqualTo(403);
    }

    private String login(String username, String password) {
        Map<String, String> body = Map.of("username", username, "password", password);
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/user/login", body, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting("code").isEqualTo(200);
        return response.getBody().get("data").toString();
    }

    private ResponseEntity<Map> get(String path, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<>(headers), Map.class);
    }
}
