package com.gongkao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GongkaoApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldProtectRolesAndSupportLocalDemoFlow() throws Exception {
        JsonNode adminLogin = login("admin", "123456");
        JsonNode teacherLogin = login("teacher", "123456");
        JsonNode studentLogin = login("user", "123456");

        assertFalse(adminLogin.path("data").path("userInfo").has("password"));

        String adminToken = adminLogin.path("data").path("token").asText();
        String teacherToken = teacherLogin.path("data").path("token").asText();
        String studentToken = studentLogin.path("data").path("token").asText();

        ResponseEntity<String> adminDashboard = exchange("/api/statistics/dashboard", HttpMethod.GET, adminToken, null);
        assertEquals(HttpStatus.OK, adminDashboard.getStatusCode());

        ResponseEntity<String> studentDashboard = exchange("/api/statistics/dashboard", HttpMethod.GET, studentToken, null);
        assertEquals(HttpStatus.FORBIDDEN, studentDashboard.getStatusCode());

        ResponseEntity<String> studentUserList = exchange("/api/user/list?pageNum=1&pageSize=5", HttpMethod.GET, studentToken, null);
        assertEquals(HttpStatus.FORBIDDEN, studentUserList.getStatusCode());

        Map<String, Object> planPayload = new HashMap<>();
        planPayload.put("userId", 1);
        planPayload.put("subjectId", 1);
        planPayload.put("title", "学生自建计划");
        planPayload.put("dailyTarget", "每天练习 30 题");
        planPayload.put("status", 1);
        ResponseEntity<String> addPlan = exchange("/api/plan/add", HttpMethod.POST, studentToken, planPayload);
        assertEquals(HttpStatus.OK, addPlan.getStatusCode());

        ResponseEntity<String> planList = exchange("/api/plan/list?pageNum=1&pageSize=20&userId=1", HttpMethod.GET, studentToken, null);
        assertEquals(HttpStatus.OK, planList.getStatusCode());
        JsonNode planJson = objectMapper.readTree(planList.getBody());
        for (JsonNode record : planJson.path("data").path("records")) {
            assertEquals(3L, record.path("userId").asLong());
        }

        Map<String, Object> coursePayload = new HashMap<>();
        coursePayload.put("subjectId", 1);
        coursePayload.put("teacherId", 1);
        coursePayload.put("title", "教师专属课程");
        coursePayload.put("level", "基础");
        coursePayload.put("summary", "用于验证 teacherId 自动绑定");
        coursePayload.put("studyHours", 8);
        coursePayload.put("status", 1);
        ResponseEntity<String> addCourse = exchange("/api/course/add", HttpMethod.POST, teacherToken, coursePayload);
        assertEquals(HttpStatus.OK, addCourse.getStatusCode());

        ResponseEntity<String> teacherCourseList = exchange("/api/course/list?pageNum=1&pageSize=20&title=教师专属课程", HttpMethod.GET, teacherToken, null);
        assertEquals(HttpStatus.OK, teacherCourseList.getStatusCode());
        JsonNode courseJson = objectMapper.readTree(teacherCourseList.getBody());
        JsonNode firstCourse = courseJson.path("data").path("records").get(0);
        assertNotNull(firstCourse);
        assertEquals(2L, firstCourse.path("teacherId").asLong());

        ResponseEntity<String> logout = exchange("/api/user/logout", HttpMethod.POST, adminToken, null);
        assertEquals(HttpStatus.OK, logout.getStatusCode());

        ResponseEntity<String> afterLogout = exchange("/api/user/info", HttpMethod.GET, adminToken, null);
        assertEquals(HttpStatus.UNAUTHORIZED, afterLogout.getStatusCode());
        assertTrue(afterLogout.getBody().contains("token已失效"));
    }

    private JsonNode login(String username, String password) throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);
        ResponseEntity<String> response = exchange("/api/user/login", HttpMethod.POST, null, payload);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        assertEquals(200, jsonNode.path("code").asInt());
        return jsonNode;
    }

    private ResponseEntity<String> exchange(String path, HttpMethod method, String token, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null && !token.isBlank()) {
            headers.setBearerAuth(token);
        }
        HttpEntity<?> entity = body == null ? new HttpEntity<>(headers) : new HttpEntity<>(body, headers);
        return restTemplate.exchange("http://127.0.0.1:" + port + path, method, entity, String.class);
    }
}
