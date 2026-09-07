package com.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentIntegratedApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void campusMainFlowsShouldWorkWithDefaultH2Profile() {
        ResponseEntity<Map> unauthenticated = restTemplate.getForEntity("/api/dashboard/stats", Map.class);
        assertThat(unauthenticated.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginSession admin = login("admin", "123456", "ADMIN", "系统管理员");
        LoginSession teacher = login("teacher", "123456", "TEACHER", "张老师");
        LoginSession student = login("student", "123456", "STUDENT", "李同学");

        ResponseEntity<Map> forbidden = exchange("/api/user/page", HttpMethod.GET, null, student.token);
        assertThat(forbidden.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertSuccess(exchange("/api/user/page", HttpMethod.GET, null, admin.token));
        assertSuccess(exchange("/api/user/teacher-list", HttpMethod.GET, null, admin.token));
        assertSuccess(exchange("/api/user/student-list", HttpMethod.GET, null, teacher.token));
        assertSuccess(exchange("/api/notice/list", HttpMethod.GET, null, student.token));
        assertSuccess(exchange("/api/dashboard/stats", HttpMethod.GET, null, teacher.token));

        String courseName = "冒烟测试课程";
        assertSuccess(exchange("/api/course", HttpMethod.POST, Map.of(
                "name", courseName,
                "credit", 2.5,
                "location", "T-101",
                "maxStudent", 30,
                "status", 1,
                "description", "默认 H2 自举环境下的课程发布验证",
                "startDate", "2026-05-01",
                "endDate", "2026-06-30"
        ), teacher.token));
        Long courseId = firstId(data(assertSuccess(exchange("/api/course/page?name=" + courseName, HttpMethod.GET, null, teacher.token))));
        assertSuccess(exchange("/api/enroll", HttpMethod.POST, Map.of("courseId", courseId), student.token));
        Long enrollId = firstId(data(assertSuccess(exchange("/api/enroll/my-page?courseId=" + courseId, HttpMethod.GET, null, student.token))));
        assertSuccess(exchange("/api/enroll/status", HttpMethod.PUT, Map.of(
                "id", enrollId,
                "status", 1,
                "score", 95.5,
                "remark", "冒烟测试成绩录入"
        ), teacher.token));

        String activityTitle = "冒烟测试活动";
        assertSuccess(exchange("/api/activity", HttpMethod.POST, Map.of(
                "title", activityTitle,
                "location", "报告厅",
                "startTime", "2026-05-10T09:00:00",
                "endTime", "2026-05-10T11:00:00",
                "maxParticipant", 50,
                "status", 1,
                "content", "默认 H2 自举环境下的活动发布验证"
        ), teacher.token));
        Long activityId = firstId(data(assertSuccess(exchange("/api/activity/page?title=" + activityTitle, HttpMethod.GET, null, teacher.token))));
        assertSuccess(exchange("/api/signup", HttpMethod.POST, Map.of("activityId", activityId), student.token));
        Long signupId = firstId(data(assertSuccess(exchange("/api/signup/my-page?activityId=" + activityId, HttpMethod.GET, null, student.token))));
        assertSuccess(exchange("/api/signup/status", HttpMethod.PUT, Map.of(
                "id", signupId,
                "status", 3,
                "remark", "冒烟测试签到"
        ), teacher.token));

        String jobTitle = "冒烟测试实习岗位";
        assertSuccess(exchange("/api/job", HttpMethod.POST, Map.of(
                "title", jobTitle,
                "company", "测试科技",
                "city", "南京",
                "salary", "5k-7k/月",
                "deadline", "2026-12-31",
                "status", 1,
                "description", "默认 H2 自举环境下的岗位发布验证"
        ), teacher.token));
        Long jobId = firstId(data(assertSuccess(exchange("/api/job/page?keyword=" + jobTitle, HttpMethod.GET, null, teacher.token))));
        assertSuccess(exchange("/api/apply", HttpMethod.POST, Map.of(
                "jobId", jobId,
                "resumeUrl", "https://example.com/smoke-resume.pdf",
                "applyNote", "冒烟测试投递"
        ), student.token));
        Long applyId = firstId(data(assertSuccess(exchange("/api/apply/my-page?jobId=" + jobId, HttpMethod.GET, null, student.token))));
        assertSuccess(exchange("/api/apply/review", HttpMethod.PUT, Map.of(
                "id", applyId,
                "status", 1,
                "reviewNote", "冒烟测试通过"
        ), teacher.token));

        String lostTitle = "冒烟测试失物";
        assertSuccess(exchange("/api/lost", HttpMethod.POST, Map.of(
                "type", 1,
                "title", lostTitle,
                "itemName", "校园卡",
                "contact", "student@campus.com",
                "location", "图书馆",
                "happenTime", "2026-05-11T13:20:00",
                "description", "默认 H2 自举环境下的失物发布验证",
                "imageUrl", "",
                "status", 1
        ), student.token));
        Long lostId = firstId(data(assertSuccess(exchange("/api/lost/page?keyword=" + lostTitle + "&myOnly=true", HttpMethod.GET, null, student.token))));
        assertSuccess(exchange("/api/lost/status", HttpMethod.PUT, Map.of(
                "id", lostId,
                "status", 2
        ), student.token));

        assertSuccess(exchange("/api/notice", HttpMethod.POST, Map.of(
                "title", "冒烟测试公告",
                "content", "默认 H2 自举环境可正常发布校园公告",
                "status", 1
        ), teacher.token));
        assertSuccess(exchange("/api/dashboard/trend", HttpMethod.GET, null, teacher.token));

        assertSuccess(exchange("/api/auth/logout", HttpMethod.POST, null, student.token));
        ResponseEntity<Map> afterLogout = exchange("/api/auth/info", HttpMethod.GET, null, student.token);
        assertThat(afterLogout.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private LoginSession login(String username, String password, String role, String nickname) {
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/auth/login", Map.of(
                "username", username,
                "password", password
        ), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("code")).isEqualTo(200);

        Map<String, Object> data = data(response);
        assertThat(data.get("token")).isInstanceOf(String.class);
        Map<String, Object> user = castMap(data.get("user"));
        assertThat(user.get("role")).isEqualTo(role);
        assertThat(user.get("nickname")).isEqualTo(nickname);
        assertThat(user).doesNotContainKey("password");
        return new LoginSession((String) data.get("token"));
    }

    private ResponseEntity<Map> exchange(String path, HttpMethod method, Object body, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return restTemplate.exchange(path, method, new HttpEntity<>(body, headers), Map.class);
    }

    private ResponseEntity<Map> assertSuccess(ResponseEntity<Map> response) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("code")).isEqualTo(200);
        return response;
    }

    private Map<String, Object> data(ResponseEntity<Map> response) {
        return castMap(response.getBody().get("data"));
    }

    private Long firstId(Map<String, Object> page) {
        List<?> records = (List<?>) page.get("records");
        assertThat(records).isNotEmpty();
        Map<String, Object> first = castMap(records.get(0));
        return ((Number) first.get("id")).longValue();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castMap(Object value) {
        return (Map<String, Object>) value;
    }

    private static class LoginSession {
        private final String token;

        private LoginSession(String token) {
            this.token = token;
        }
    }
}
