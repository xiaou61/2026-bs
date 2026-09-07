package com.fraud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AntiFraudPlatformApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void antiFraudMainFlowsShouldWorkWithDefaultH2Profile() {
        ResponseEntity<Map> unauthenticated = restTemplate.getForEntity("/api/dashboard/stats", Map.class);
        assertThat(unauthenticated.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginSession admin = login("admin", "123456", "ADMIN", "系统管理员");
        LoginSession analyst = login("analyst", "123456", "ANALYST", "风控分析员");
        LoginSession user = login("user1", "123456", "USER", "商户甲");

        ResponseEntity<Map> forbidden = exchange("/api/user/page", HttpMethod.GET, null, user.token);
        assertThat(forbidden.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertSuccess(exchange("/api/user/page", HttpMethod.GET, null, admin.token));
        assertSuccess(exchange("/api/dashboard/stats", HttpMethod.GET, null, analyst.token));
        assertSuccess(exchange("/api/blacklist/page", HttpMethod.GET, null, analyst.token));
        assertSuccess(exchange("/api/rule/page", HttpMethod.GET, null, analyst.token));

        Map<String, Object> event = Map.of(
                "accountNo", "ACC-90001",
                "deviceId", "DEV-556677",
                "ip", "10.10.10.9",
                "amount", new BigDecimal("8888.88"),
                "eventType", "PAY",
                "channel", "API",
                "remark", "冒烟测试高风险交易"
        );
        Map<String, Object> reportedEvent = data(assertSuccess(exchange("/api/event/report", HttpMethod.POST, event, user.token)));
        assertThat(reportedEvent.get("riskLevel")).isEqualTo("HIGH");
        assertThat(((Number) reportedEvent.get("riskScore")).intValue()).isGreaterThanOrEqualTo(80);

        Map<String, Object> myAlerts = data(assertSuccess(exchange("/api/alert/my?pageNum=1&pageSize=10", HttpMethod.GET, null, user.token)));
        Long alertId = firstId(myAlerts);
        assertThat(alertId).isNotNull();

        assertSuccess(exchange("/api/alert/assign", HttpMethod.PUT, Map.of("id", alertId, "assigneeId", 2), analyst.token));
        assertSuccess(exchange("/api/alert/handle", HttpMethod.PUT, Map.of(
                "id", alertId,
                "status", 2,
                "handleResult", "确认欺诈",
                "handleRemark", "冒烟测试处置"
        ), analyst.token));

        assertSuccess(exchange("/api/case", HttpMethod.POST, Map.of(
                "alertId", alertId,
                "caseType", "PAY_FRAUD",
                "priority", 3,
                "summary", "冒烟测试案件"
        ), analyst.token));
        assertSuccess(exchange("/api/case/page", HttpMethod.GET, null, analyst.token));

        assertSuccess(exchange("/api/appeal", HttpMethod.POST, Map.of(
                "alertId", alertId,
                "content", "冒烟测试申诉内容"
        ), user.token));
        Map<String, Object> appeals = data(assertSuccess(exchange("/api/appeal/page?status=0", HttpMethod.GET, null, analyst.token)));
        Long appealId = firstId(appeals);
        assertThat(appealId).isNotNull();
        assertSuccess(exchange("/api/appeal/reply", HttpMethod.PUT, Map.of(
                "id", appealId,
                "reply", "冒烟测试复核完成",
                "alertStatus", 3
        ), analyst.token));

        assertSuccess(exchange("/api/announcement", HttpMethod.POST, Map.of(
                "title", "冒烟测试公告",
                "content", "默认 H2 自举环境可正常发布公告",
                "status", 1
        ), admin.token));
        assertSuccess(exchange("/api/announcement/list", HttpMethod.GET, null, user.token));
        assertSuccess(exchange("/api/log/page", HttpMethod.GET, null, admin.token));

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
