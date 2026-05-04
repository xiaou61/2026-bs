package com.kindergarten;

import com.kindergarten.common.Result;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KindergartenApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void permissionBoundariesShouldWork() {
        ResponseEntity<Result<Object>> anonInfo = exchange("/api/auth/info", HttpMethod.GET, null, new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, anonInfo.getStatusCode());

        ResponseEntity<Result<List<Map<String, Object>>>> notices = exchange("/api/notice/public/list", HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Map<String, Object>>>>() {});
        assertEquals(HttpStatus.OK, notices.getStatusCode());

        LoginResult admin = login("admin", "123456");
        LoginResult teacher = login("teacher", "123456");
        LoginResult teacher2 = login("teacher2", "123456");
        LoginResult parent = login("parent", "123456");
        LoginResult parent2 = login("parent2", "123456");

        assertFalse(((Map<?, ?>) admin.data.get("userInfo")).containsKey("password"));

        ResponseEntity<Result<Object>> parentStats = exchange("/api/statistics/dashboard", HttpMethod.GET, bearer(parent.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, parentStats.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> teacherChildList = exchange("/api/child/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(teacher.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, teacherChildList.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> parentChildList = exchange("/api/child/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(parent.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, parentChildList.getStatusCode());
        List<Map<String, Object>> parentChildren = (List<Map<String, Object>>) parentChildList.getBody().getData().get("list");
        assertEquals(1, parentChildren.size());
        assertEquals(1, ((Number) parentChildren.get(0).get("id")).intValue());

        ResponseEntity<Result<Map<String, Object>>> parentSchedules = exchange("/api/schedule/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(parent.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, parentSchedules.getStatusCode());
        List<Map<String, Object>> parentScheduleItems = (List<Map<String, Object>>) parentSchedules.getBody().getData().get("list");
        assertEquals(1, parentScheduleItems.size());

        ResponseEntity<Result<Object>> crossAttendanceUpdate = exchange("/api/attendance/update", HttpMethod.PUT, jsonBearer(teacher.token, Map.of(
                "id", 3,
                "attendanceStatus", "present"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, crossAttendanceUpdate.getStatusCode());

        ResponseEntity<Result<Object>> ownAttendanceUpdate = exchange("/api/attendance/update", HttpMethod.PUT, jsonBearer(teacher.token, Map.of(
                "id", 1,
                "attendanceStatus", "late",
                "remark", "晨检后延迟入园"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, ownAttendanceUpdate.getStatusCode());

        ResponseEntity<Result<Object>> crossHealthUpdate = exchange("/api/health/save", HttpMethod.POST, jsonBearer(teacher2.token, Map.of(
                "id", 1,
                "childId", 1,
                "recordDate", "2026-03-16",
                "temperature", new BigDecimal("36.7"),
                "healthStatus", "正常"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, crossHealthUpdate.getStatusCode());

        ResponseEntity<Result<Object>> ownHealthUpdate = exchange("/api/health/save", HttpMethod.POST, jsonBearer(teacher.token, Map.of(
                "id", 1,
                "childId", 1,
                "recordDate", "2026-03-16",
                "temperature", new BigDecimal("36.6"),
                "healthStatus", "正常",
                "emotionStatus", "活跃",
                "attendanceAdvice", "可正常入园"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, ownHealthUpdate.getStatusCode());

        ResponseEntity<Result<Object>> parentFakeFeedback = exchange("/api/feedback/add", HttpMethod.POST, jsonBearer(parent2.token, Map.of(
                "childId", 1,
                "feedbackType", "安全",
                "feedbackScore", 88,
                "feedbackContent", "伪造他人幼儿反馈"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, parentFakeFeedback.getStatusCode());

        ResponseEntity<Result<Object>> parentFeedback = exchange("/api/feedback/add", HttpMethod.POST, jsonBearer(parent.token, Map.of(
                "childId", 1,
                "feedbackType", "成长",
                "feedbackScore", 96,
                "feedbackContent", "今天分享欲很强"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, parentFeedback.getStatusCode());

        ResponseEntity<Result<Object>> teacherCrossReply = exchange("/api/feedback/reply", HttpMethod.PUT, jsonBearer(teacher2.token, Map.of(
                "id", 1,
                "replyContent", "越权回复"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, teacherCrossReply.getStatusCode());

        ResponseEntity<Result<Object>> teacherReply = exchange("/api/feedback/reply", HttpMethod.PUT, jsonBearer(teacher.token, Map.of(
                "id", 1,
                "replyContent", "已收到，会继续跟进"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, teacherReply.getStatusCode());

        ResponseEntity<Result<Object>> recipeDelete = exchange("/api/recipe/delete/3", HttpMethod.DELETE, bearer(teacher.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, recipeDelete.getStatusCode());

        ResponseEntity<Result<Object>> logout = exchange("/api/auth/logout", HttpMethod.POST, bearer(parent.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, logout.getStatusCode());

        ResponseEntity<Result<Object>> afterLogout = exchange("/api/child/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(parent.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, afterLogout.getStatusCode());
    }

    private LoginResult login(String username, String password) {
        ResponseEntity<Result<Map<String, Object>>> response = exchange("/api/auth/login", HttpMethod.POST, json(Map.of("username", username, "password", password)), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        return new LoginResult(String.valueOf(response.getBody().getData().get("token")), response.getBody().getData());
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
