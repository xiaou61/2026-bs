package com.teachres;

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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MathEvalApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loginPermissionCourseEvaluationAndLogoutFlowWork() {
        ResponseEntity<Map> anonymousInfo = restTemplate.getForEntity(url("/api/auth/info"), Map.class);
        assertEquals(HttpStatus.UNAUTHORIZED, anonymousInfo.getStatusCode());

        ResponseEntity<Map> publicNotice = restTemplate.getForEntity(url("/api/notice/public/list"), Map.class);
        assertEquals(HttpStatus.OK, publicNotice.getStatusCode());

        Map<String, Object> adminLogin = login("admin", "123456");
        Map<String, Object> teacherLogin = login("teacher", "123456");
        Map<String, Object> studentLogin = login("student", "123456");
        Map<String, Object> student2Login = login("student2", "123456");

        String adminToken = String.valueOf(adminLogin.get("token"));
        String teacherToken = String.valueOf(teacherLogin.get("token"));
        String studentToken = String.valueOf(studentLogin.get("token"));
        String student2Token = String.valueOf(student2Login.get("token"));
        Map<String, Object> adminUser = asMap(adminLogin.get("userInfo"));
        assertEquals("admin", adminUser.get("role"));
        assertFalse(adminUser.containsKey("password"));

        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.GET, "/api/statistics/dashboard", adminToken, null).getStatusCode());
        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.GET, "/api/statistics/dashboard", teacherToken, null).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, exchangeWithToken(HttpMethod.GET, "/api/statistics/dashboard", studentToken, null).getStatusCode());

        assertEquals(HttpStatus.FORBIDDEN, exchangeWithToken(HttpMethod.POST, "/api/category/add", studentToken,
                Map.of("name", "学生越权分类", "code", "STUDENT_FORBIDDEN")).getStatusCode());

        String courseName = "教师课程" + System.currentTimeMillis();
        Map<String, Object> coursePayload = new LinkedHashMap<>();
        coursePayload.put("courseName", courseName);
        coursePayload.put("courseCode", "T" + System.currentTimeMillis());
        coursePayload.put("categoryId", 1);
        coursePayload.put("teacherId", 999);
        coursePayload.put("credit", 2);
        coursePayload.put("term", "2025-2026-2");
        coursePayload.put("description", "验证教师归属绑定");
        coursePayload.put("status", 1);
        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.POST, "/api/course/add", teacherToken, coursePayload).getStatusCode());

        ResponseEntity<Map> teacherCourses = exchangeWithToken(HttpMethod.GET,
                "/api/course/list?pageNum=1&pageSize=10&courseName=" + courseName, teacherToken, null);
        assertEquals(HttpStatus.OK, teacherCourses.getStatusCode());
        List<?> courseList = (List<?>) asMap(asMap(teacherCourses.getBody()).get("data")).get("list");
        Map<String, Object> firstCourse = asMap(courseList.get(0));
        assertEquals(2, ((Number) firstCourse.get("teacherId")).intValue());

        assertEquals(HttpStatus.FORBIDDEN, exchangeWithToken(HttpMethod.POST, "/api/course/add", studentToken, coursePayload).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, exchangeWithToken(HttpMethod.GET, "/api/evaluation/task/records?taskId=1", studentToken, null).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, exchangeWithToken(HttpMethod.GET, "/api/evaluation/detail/1", student2Token, null).getStatusCode());
        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.GET, "/api/evaluation/task/summary?taskId=1", teacherToken, null).getStatusCode());

        Map<String, Object> evaluationPayload = new LinkedHashMap<>();
        evaluationPayload.put("taskId", 3);
        evaluationPayload.put("commentContent", "验证学生提交评价");
        evaluationPayload.put("items", List.of(
                Map.of("indicatorId", 1, "score", 90),
                Map.of("indicatorId", 2, "score", 91),
                Map.of("indicatorId", 3, "score", 92),
                Map.of("indicatorId", 4, "score", 93),
                Map.of("indicatorId", 5, "score", 94)
        ));
        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.POST, "/api/evaluation/submit", student2Token, evaluationPayload).getStatusCode());

        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.POST, "/api/auth/logout", adminToken, null).getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED, exchangeWithToken(HttpMethod.GET, "/api/auth/info", adminToken, null).getStatusCode());
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
