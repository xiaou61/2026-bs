package com.teacher.eval;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeacherEvaluationApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void smokeTeacherEvaluationHappyPathAndAuthGuards() throws Exception {
        ResponseEntity<String> anonymousStats = restTemplate.getForEntity("/api/dashboard/stats", String.class);
        assertThat(anonymousStats.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginSession admin = login("admin", "123456", "ADMIN");
        LoginSession teacher = login("teacher1", "123456", "TEACHER");
        LoginSession student = login("student1", "123456", "STUDENT");

        assertGetOk("/api/user/page?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/subject/page?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/class/page?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/teacher/page?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/indicator/page?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/dashboard/trend", admin.token());

        ResponseEntity<String> studentUserPage = exchange(
                "/api/user/page?pageNum=1&pageSize=10",
                HttpMethod.GET,
                null,
                student.token()
        );
        assertThat(studentUserPage.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        Map<String, Object> studentTaskPage = assertGetOk("/api/task/my-page?pageNum=1&pageSize=10", student.token());
        assertThat(records(studentTaskPage)).extracting(item -> ((Number) item.get("id")).longValue()).contains(1L);

        Map<String, Object> createdRecord = assertPostOk("/api/record", Map.of(
                "taskId", 1,
                "attitudeScore", 95,
                "contentScore", 94,
                "methodScore", 93,
                "manageScore", 92,
                "homeworkScore", 91,
                "commentText", "课堂节奏清楚，互动充分。"
        ), student.token());
        long recordId = ((Number) createdRecord.get("id")).longValue();
        assertThat(recordId).isGreaterThan(1L);

        Map<String, Object> teacherRecordPage = assertGetOk("/api/record/my-page?pageNum=1&pageSize=10", teacher.token());
        assertThat(records(teacherRecordPage)).extracting(item -> ((Number) item.get("id")).longValue()).contains(recordId);

        Map<String, Object> createdAppeal = assertPostOk("/api/appeal", Map.of(
                "recordId", recordId,
                "reasonText", "本次评分样本较少，申请管理员复核。"
        ), teacher.token());
        long appealId = ((Number) createdAppeal.get("id")).longValue();
        assertThat(appealId).isGreaterThan(1L);

        assertPutOk("/api/appeal/handle", Map.of(
                "id", appealId,
                "status", "PASSED",
                "replyText", "已复核并记录处理意见。"
        ), admin.token());

        assertPostOk("/api/notice", Map.of(
                "title", "069 冒烟测试公告",
                "contentText", "管理员公告发布链路验证通过。",
                "status", 1
        ), admin.token());

        ResponseEntity<String> logout = exchange("/api/auth/logout", HttpMethod.POST, Map.of(), admin.token());
        assertThat(logout.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> afterLogout = exchange("/api/auth/info", HttpMethod.GET, null, admin.token());
        assertThat(afterLogout.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private LoginSession login(String username, String password, String expectedRole) throws Exception {
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/auth/login",
                jsonEntity(Map.of("username", username, "password", password), null),
                String.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = body(response);
        assertThat(body.get("code")).isEqualTo(200);

        Map<String, Object> data = data(body);
        String token = String.valueOf(data.get("token"));
        assertThat(token).isNotBlank();

        Map<String, Object> user = castMap(data.get("user"));
        assertThat(user.get("username")).isEqualTo(username);
        assertThat(user.get("role")).isEqualTo(expectedRole);
        assertThat(user).doesNotContainKey("password");
        return new LoginSession(token);
    }

    private Map<String, Object> assertGetOk(String url, String token) throws Exception {
        ResponseEntity<String> response = exchange(url, HttpMethod.GET, null, token);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = body(response);
        assertThat(body.get("code")).isEqualTo(200);
        return data(body);
    }

    private Map<String, Object> assertPostOk(String url, Object payload, String token) throws Exception {
        ResponseEntity<String> response = exchange(url, HttpMethod.POST, payload, token);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = body(response);
        assertThat(body.get("code")).isEqualTo(200);
        Object data = body.get("data");
        return data == null ? Map.of() : castMap(data);
    }

    private void assertPutOk(String url, Object payload, String token) throws Exception {
        ResponseEntity<String> response = exchange(url, HttpMethod.PUT, payload, token);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = body(response);
        assertThat(body.get("code")).isEqualTo(200);
    }

    private ResponseEntity<String> exchange(String url, HttpMethod method, Object payload, String token) {
        return restTemplate.exchange(url, method, jsonEntity(payload, token), String.class);
    }

    private HttpEntity<Object> jsonEntity(Object payload, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.setBearerAuth(token);
        }
        return new HttpEntity<>(payload, headers);
    }

    private Map<String, Object> body(ResponseEntity<String> response) throws Exception {
        return objectMapper.readValue(response.getBody(), new TypeReference<>() {
        });
    }

    private Map<String, Object> data(Map<String, Object> body) {
        return castMap(body.get("data"));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castMap(Object value) {
        return (Map<String, Object>) value;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> records(Map<String, Object> page) {
        return (List<Map<String, Object>>) page.get("records");
    }

    private record LoginSession(String token) {
    }
}
