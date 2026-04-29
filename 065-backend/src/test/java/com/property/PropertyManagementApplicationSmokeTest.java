package com.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PropertyManagementApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void propertyMainFlowsShouldWorkWithDefaultH2Profile() {
        ResponseEntity<Map> unauthenticated = restTemplate.getForEntity("/api/dashboard/stats", Map.class);
        assertThat(unauthenticated.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginSession admin = login("admin", "123456", "ADMIN", "系统管理员");
        LoginSession staff = login("staff", "123456", "STAFF", "物业管家");
        LoginSession owner = login("owner", "123456", "OWNER", "张业主");

        ResponseEntity<Map> forbidden = exchange("/api/user/page", HttpMethod.GET, null, owner.token);
        assertThat(forbidden.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertSuccess(exchange("/api/user/page", HttpMethod.GET, null, admin.token));
        assertSuccess(exchange("/api/dashboard/stats", HttpMethod.GET, null, staff.token));
        assertSuccess(exchange("/api/building/list", HttpMethod.GET, null, owner.token));
        assertSuccess(exchange("/api/house/list", HttpMethod.GET, null, owner.token));
        assertSuccess(exchange("/api/house/list?ownerOnly=true", HttpMethod.GET, null, owner.token));
        assertSuccess(exchange("/api/user/owner-list", HttpMethod.GET, null, staff.token));
        assertSuccess(exchange("/api/parking/list", HttpMethod.GET, null, owner.token));
        assertSuccess(exchange("/api/announcement/list", HttpMethod.GET, null, owner.token));

        assertSuccess(exchange("/api/announcement", HttpMethod.POST, Map.of(
                "title", "冒烟测试公告",
                "content", "默认 H2 自举环境可正常发布物业公告",
                "status", 1
        ), admin.token));

        assertSuccess(exchange("/api/fee", HttpMethod.POST, Map.of(
                "houseId", 1,
                "amount", 188.80,
                "feeMonth", "2026-04",
                "remark", "冒烟测试物业费"
        ), staff.token));
        Map<String, Object> myFees = data(assertSuccess(exchange("/api/fee/my-page?status=0", HttpMethod.GET, null, owner.token)));
        Long feeId = firstId(myFees);
        assertSuccess(exchange("/api/fee/pay/" + feeId, HttpMethod.PUT, null, owner.token));

        assertSuccess(exchange("/api/repair", HttpMethod.POST, Map.of(
                "houseId", 1,
                "title", "冒烟测试报修",
                "content", "入户门门锁需要检查"
        ), owner.token));
        Map<String, Object> pendingRepairs = data(assertSuccess(exchange("/api/repair/page?status=0", HttpMethod.GET, null, staff.token)));
        Long repairId = firstId(pendingRepairs);
        assertSuccess(exchange("/api/repair/status", HttpMethod.PUT, Map.of(
                "id", repairId,
                "status", 1,
                "assigneeId", 2,
                "reply", "已安排物业管家跟进"
        ), staff.token));
        assertSuccess(exchange("/api/repair/status", HttpMethod.PUT, Map.of(
                "id", repairId,
                "status", 2,
                "assigneeId", 2,
                "reply", "门锁已完成检修"
        ), staff.token));

        assertSuccess(exchange("/api/complaint", HttpMethod.POST, Map.of(
                "title", "冒烟测试投诉",
                "content", "楼道杂物需要清理"
        ), owner.token));
        Map<String, Object> pendingComplaints = data(assertSuccess(exchange("/api/complaint/page?status=0", HttpMethod.GET, null, staff.token)));
        Long complaintId = firstId(pendingComplaints);
        assertSuccess(exchange("/api/complaint/reply", HttpMethod.PUT, Map.of(
                "id", complaintId,
                "reply", "已通知保洁处理"
        ), staff.token));

        assertSuccess(exchange("/api/visitor", HttpMethod.POST, Map.of(
                "visitorName", "冒烟访客",
                "visitorPhone", "13900059999",
                "visitTime", LocalDateTime.now().plusDays(2).withNano(0).toString(),
                "remark", "冒烟测试访客登记"
        ), owner.token));
        Map<String, Object> pendingVisitors = data(assertSuccess(exchange("/api/visitor/page?status=0", HttpMethod.GET, null, staff.token)));
        Long visitorId = firstId(pendingVisitors);
        assertSuccess(exchange("/api/visitor/status", HttpMethod.PUT, Map.of(
                "id", visitorId,
                "status", 1,
                "remark", "同意来访"
        ), staff.token));
        assertSuccess(exchange("/api/dashboard/trend", HttpMethod.GET, null, staff.token));

        assertSuccess(exchange("/api/auth/logout", HttpMethod.POST, null, owner.token));
        ResponseEntity<Map> afterLogout = exchange("/api/auth/info", HttpMethod.GET, null, owner.token);
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
