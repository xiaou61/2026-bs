package com.course;

import com.course.common.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseManagementApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loginPermissionOwnershipAndLogoutShouldWork() {
        ResponseEntity<Result<Object>> anonInfo = exchange("/api/auth/info", HttpMethod.GET, null, new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, anonInfo.getStatusCode());
        assertEquals(401, anonInfo.getBody().getCode());

        LoginResult admin = login("admin", "123456");
        LoginResult teacher = login("teacher", "123456");
        LoginResult student = login("student", "123456");
        LoginResult student2 = login("student2", "123456");

        assertFalse(((Map<?, ?>) admin.data.get("userInfo")).containsKey("password"));

        ResponseEntity<Result<Map<String, Object>>> statsAsStudent = exchange("/api/statistics/dashboard", HttpMethod.GET, bearer(student.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.FORBIDDEN, statsAsStudent.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> statsAsTeacher = exchange("/api/statistics/dashboard", HttpMethod.GET, bearer(teacher.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, statsAsTeacher.getStatusCode());
        assertEquals(200, statsAsTeacher.getBody().getCode());

        Map<String, Object> resourceBody = new HashMap<>();
        resourceBody.put("scheduleId", 1);
        resourceBody.put("courseId", 1);
        resourceBody.put("teacherId", 1);
        resourceBody.put("title", "Smoke Resource");
        resourceBody.put("resourceType", "pdf");
        resourceBody.put("resourceUrl", "/resource/smoke.pdf");
        resourceBody.put("contentDesc", "smoke");
        ResponseEntity<Result<Object>> addResource = exchange("/api/resource/add", HttpMethod.POST, jsonBearer(teacher.token, resourceBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, addResource.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> teacherResources = exchange("/api/resource/list?pageNum=1&pageSize=20&scheduleId=1", HttpMethod.GET, bearer(teacher.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, teacherResources.getStatusCode());
        Map<String, Object> pageData = teacherResources.getBody().getData();
        assertNotNull(pageData);
        Iterable<?> list = (Iterable<?>) pageData.get("list");
        Map<?, ?> createdResource = null;
        for (Object item : list) {
            Map<?, ?> map = (Map<?, ?>) item;
            if ("Smoke Resource".equals(map.get("title"))) {
                createdResource = map;
                break;
            }
        }
        assertNotNull(createdResource);
        assertEquals(2, ((Number) createdResource.get("teacherId")).intValue());

        Map<String, Object> updateResourceBody = new HashMap<>();
        updateResourceBody.put("id", createdResource.get("id"));
        updateResourceBody.put("scheduleId", 1);
        updateResourceBody.put("courseId", 1);
        updateResourceBody.put("title", "Hacked Resource");
        updateResourceBody.put("resourceType", "pdf");
        updateResourceBody.put("resourceUrl", "/resource/hacked.pdf");
        ResponseEntity<Result<Object>> studentUpdateResource = exchange("/api/resource/update", HttpMethod.PUT, jsonBearer(student.token, updateResourceBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, studentUpdateResource.getStatusCode());

        Map<String, Object> selectionBody = new HashMap<>();
        selectionBody.put("scheduleId", 1);
        ResponseEntity<Result<Object>> selectCourse = exchange("/api/selection/select", HttpMethod.POST, jsonBearer(student2.token, selectionBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, selectCourse.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> studentSelections = exchange("/api/selection/list?pageNum=1&pageSize=20&scheduleId=1", HttpMethod.GET, bearer(student2.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        Map<String, Object> selectionPage = studentSelections.getBody().getData();
        Map<?, ?> createdSelection = null;
        for (Object item : (Iterable<?>) selectionPage.get("list")) {
            Map<?, ?> map = (Map<?, ?>) item;
            if (((Number) map.get("studentId")).longValue() == 4L && ((Number) map.get("scheduleId")).longValue() == 1L) {
                createdSelection = map;
                break;
            }
        }
        assertNotNull(createdSelection);

        Map<String, Object> scoreBody = new HashMap<>();
        scoreBody.put("selectionId", createdSelection.get("id"));
        scoreBody.put("usualScore", 87);
        scoreBody.put("examScore", 93);
        ResponseEntity<Result<Object>> teacherSaveScore = exchange("/api/score/save", HttpMethod.POST, jsonBearer(teacher.token, scoreBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, teacherSaveScore.getStatusCode());

        ResponseEntity<Result<Object>> studentSaveScore = exchange("/api/score/save", HttpMethod.POST, jsonBearer(student.token, scoreBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, studentSaveScore.getStatusCode());

        Map<String, Object> evaluationBody = new HashMap<>();
        evaluationBody.put("scheduleId", 1);
        evaluationBody.put("evaluationScore", 96);
        evaluationBody.put("evaluationContent", "很好");
        ResponseEntity<Result<Object>> student2Evaluation = exchange("/api/evaluation/add", HttpMethod.POST, jsonBearer(student2.token, evaluationBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, student2Evaluation.getStatusCode());

        ResponseEntity<Result<Object>> logout = exchange("/api/auth/logout", HttpMethod.POST, bearer(student2.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, logout.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> afterLogout = exchange("/api/selection/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(student2.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, afterLogout.getStatusCode());
    }

    private LoginResult login(String username, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        ResponseEntity<Result<Map<String, Object>>> response = exchange("/api/auth/login", HttpMethod.POST, json(body), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getCode());
        return new LoginResult((String) response.getBody().getData().get("token"), response.getBody().getData());
    }

    private HttpEntity<Map<String, Object>> json(Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<Map<String, Object>> jsonBearer(String token, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<Void> bearer(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    private <T> ResponseEntity<T> exchange(String path, HttpMethod method, HttpEntity<?> entity, ParameterizedTypeReference<T> type) {
        HttpEntity<?> actualEntity = entity == null ? new HttpEntity<>(new HttpHeaders()) : entity;
        return restTemplate.exchange("http://127.0.0.1:" + port + path, method, actualEntity, type);
    }

    private record LoginResult(String token, Map<String, Object> data) {}
}
