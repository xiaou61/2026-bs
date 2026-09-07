package com.classic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClassicDietServiceApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void classicDietMainFlowsShouldWorkWithDefaultH2Profile() {
        ResponseEntity<Map> unauthenticated = restTemplate.getForEntity("/api/dashboard/stats", Map.class);
        assertThat(unauthenticated.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginSession admin = login("admin", "123456", "ADMIN", "平台管理员");
        LoginSession user = login("user", "123456", "USER", "调理用户");

        ResponseEntity<Map> forbidden = exchange("/api/user/page", HttpMethod.GET, null, user.token);
        assertThat(forbidden.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertSuccess(exchange("/api/user/page", HttpMethod.GET, null, admin.token));
        assertSuccess(exchange("/api/dashboard/stats", HttpMethod.GET, null, admin.token));
        assertSuccess(exchange("/api/category/list", HttpMethod.GET, null, user.token));
        assertSuccess(exchange("/api/ingredient/list", HttpMethod.GET, null, user.token));
        assertSuccess(exchange("/api/formula/list", HttpMethod.GET, null, user.token));
        assertSuccess(exchange("/api/plan/list", HttpMethod.GET, null, user.token));

        assertSuccess(exchange("/api/announcement", HttpMethod.POST, Map.of(
                "title", "冒烟测试公告",
                "content", "默认 H2 自举环境可正常发布公告",
                "status", 1
        ), admin.token));

        assertSuccess(exchange("/api/favorite/toggle", HttpMethod.POST, Map.of(
                "targetType", "FORMULA",
                "targetId", 2
        ), user.token));
        Map<String, Object> favorites = data(assertSuccess(exchange("/api/favorite/my-page?targetType=FORMULA", HttpMethod.GET, null, user.token)));
        assertThat(firstId(favorites)).isNotNull();

        assertSuccess(exchange("/api/service", HttpMethod.POST, Map.of(
                "planId", 1,
                "contactName", "李四",
                "contactPhone", "13900049999",
                "appointmentDate", LocalDate.now().plusDays(3).toString(),
                "remark", "冒烟测试调理预约"
        ), user.token));
        Map<String, Object> myOrders = data(assertSuccess(exchange("/api/service/my-page?status=0", HttpMethod.GET, null, user.token)));
        Long serviceOrderId = firstId(myOrders);
        assertSuccess(exchange("/api/service/status", HttpMethod.PUT, Map.of(
                "id", serviceOrderId,
                "status", 1,
                "adminReply", "冒烟测试已确认"
        ), admin.token));

        assertSuccess(exchange("/api/constitution", HttpMethod.POST, Map.of(
                "constitutionType", "气虚质",
                "symptomDesc", "冒烟测试：乏力、自汗、气短"
        ), user.token));
        Map<String, Object> pendingRecords = data(assertSuccess(exchange("/api/constitution/page?status=0", HttpMethod.GET, null, admin.token)));
        Long recordId = firstId(pendingRecords);
        assertSuccess(exchange("/api/constitution/reply", HttpMethod.PUT, Map.of(
                "id", recordId,
                "suggestion", "冒烟测试建议：规律作息，配合健脾补气方案"
        ), admin.token));
        assertSuccess(exchange("/api/constitution/my-page?status=1", HttpMethod.GET, null, user.token));
        assertSuccess(exchange("/api/dashboard/order-trend", HttpMethod.GET, null, admin.token));

        assertSuccess(exchange("/api/auth/logout", HttpMethod.POST, null, admin.token));
        ResponseEntity<Map> afterLogout = exchange("/api/auth/info", HttpMethod.GET, null, admin.token);
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
