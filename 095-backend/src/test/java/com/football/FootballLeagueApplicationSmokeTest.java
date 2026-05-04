package com.football;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FootballLeagueApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void smokeFlow() {
        assertEquals(HttpStatus.UNAUTHORIZED, restTemplate.getForEntity(url("/api/auth/info"), Map.class).getStatusCode());
        assertEquals(HttpStatus.OK, restTemplate.getForEntity(url("/api/match/public/list"), Map.class).getStatusCode());
        assertEquals(HttpStatus.OK, restTemplate.getForEntity(url("/api/news/public/list"), Map.class).getStatusCode());
        assertEquals(HttpStatus.OK, restTemplate.getForEntity(url("/api/standing/list"), Map.class).getStatusCode());

        LoginResult admin = login("admin", "123456");
        LoginResult manager = login("manager", "123456");
        LoginResult fan = login("fan", "123456");

        assertEquals("ADMIN", ((Map<?, ?>) admin.body().get("user")).get("role"));
        assertFalse(((Map<?, ?>) admin.body().get("user")).containsKey("password"));

        assertEquals(HttpStatus.FORBIDDEN,
                exchange("/api/statistics/dashboard", HttpMethod.GET, null, fan.token(), Map.class).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN,
                exchange("/api/user/list", HttpMethod.GET, null, manager.token(), Map.class).getStatusCode());

        ResponseEntity<Map> managerStats = exchange("/api/statistics/dashboard", HttpMethod.GET, null, manager.token(), Map.class);
        assertEquals(HttpStatus.OK, managerStats.getStatusCode());
        assertEquals(200, managerStats.getBody().get("code"));

        ResponseEntity<Map> followToggle = exchange("/api/follow/toggle", HttpMethod.POST,
                "{\"teamId\":2}", fan.token(), Map.class);
        assertEquals(HttpStatus.OK, followToggle.getStatusCode());
        assertEquals(200, followToggle.getBody().get("code"));

        assertEquals(HttpStatus.OK, exchange("/api/auth/logout", HttpMethod.POST, "{}", fan.token(), Map.class).getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED,
                exchange("/api/follow/list?pageNum=1&pageSize=10", HttpMethod.GET, null, fan.token(), Map.class).getStatusCode());
    }

    private LoginResult login(String username, String password) {
        ResponseEntity<Map> response = exchange("/api/auth/login", HttpMethod.POST,
                "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}", null, Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().get("code"));
        Map<?, ?> data = (Map<?, ?>) response.getBody().get("data");
        assertNotNull(data);
        Object token = data.get("token");
        assertTrue(token instanceof String && !((String) token).isBlank());
        return new LoginResult((String) token, data);
    }

    private <T> ResponseEntity<T> exchange(String path, HttpMethod method, String body, String token, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.setBearerAuth(token);
        }
        return restTemplate.exchange(url(path), method, new HttpEntity<>(body, headers), responseType);
    }

    private String url(String path) {
        return "http://127.0.0.1:" + port + path;
    }

    private record LoginResult(String token, Map<?, ?> body) {
    }
}
