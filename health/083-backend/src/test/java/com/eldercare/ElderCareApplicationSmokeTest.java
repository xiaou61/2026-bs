package com.eldercare;

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

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ElderCareApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loginPermissionAndLogoutFlowWork() {
        ResponseEntity<Map> anonymousInfo = restTemplate.getForEntity(url("/api/auth/info"), Map.class);
        assertEquals(HttpStatus.UNAUTHORIZED, anonymousInfo.getStatusCode());

        Map<String, Object> adminLogin = login("admin", "123456");
        String adminToken = String.valueOf(adminLogin.get("token"));
        Map<String, Object> adminUser = asMap(adminLogin.get("userInfo"));
        assertEquals("admin", adminUser.get("role"));
        assertFalse(adminUser.containsKey("password"));

        ResponseEntity<Map> dashboard = exchangeWithToken(HttpMethod.GET, "/api/statistics/dashboard", adminToken, null);
        assertEquals(HttpStatus.OK, dashboard.getStatusCode());

        ResponseEntity<Map> receptionResultAdd = exchange(
                HttpMethod.POST,
                "/api/result/add",
                "reception",
                "123456",
                Map.of("appointmentId", 3, "itemId", 1, "itemValue", "5.5", "conclusion", "登记测试")
        );
        assertEquals(HttpStatus.FORBIDDEN, receptionResultAdd.getStatusCode());

        ResponseEntity<Map> doctorResultAdd = exchange(
                HttpMethod.POST,
                "/api/result/add",
                "doctor",
                "123456",
                Map.of("appointmentId", 3, "elderId", 999, "itemId", 1, "itemValue", "5.5", "conclusion", "正常")
        );
        assertEquals(HttpStatus.OK, doctorResultAdd.getStatusCode());

        ResponseEntity<Map> logout = exchangeWithToken(HttpMethod.POST, "/api/auth/logout", adminToken, null);
        assertEquals(HttpStatus.OK, logout.getStatusCode());

        ResponseEntity<Map> expiredInfo = exchangeWithToken(HttpMethod.GET, "/api/auth/info", adminToken, null);
        assertEquals(HttpStatus.UNAUTHORIZED, expiredInfo.getStatusCode());
    }

    private Map<String, Object> login(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(Map.of("username", username, "password", password), headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url("/api/auth/login"), entity, Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        Map<String, Object> data = asMap(body.get("data"));
        assertNotNull(data.get("token"));
        return data;
    }

    private ResponseEntity<Map> exchange(HttpMethod method, String path, String username, String password, Object body) {
        String token = String.valueOf(login(username, password).get("token"));
        return exchangeWithToken(method, path, token, body);
    }

    private ResponseEntity<Map> exchangeWithToken(HttpMethod method, String path, String token, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url(path), method, entity, Map.class);
    }

    private String url(String path) {
        return "http://127.0.0.1:" + port + path;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> asMap(Object value) {
        return value == null ? Map.of() : new LinkedHashMap<>((Map<String, Object>) value);
    }
}
