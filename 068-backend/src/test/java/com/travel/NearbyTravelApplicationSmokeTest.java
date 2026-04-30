package com.travel;

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
class NearbyTravelApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void personalTravelMainFlowsShouldWorkWithDefaultH2Profile() {
        ResponseEntity<Map> unauthenticated = restTemplate.getForEntity("/api/dashboard/stats", Map.class);
        assertThat(unauthenticated.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginSession admin = login("admin", "123456", "ADMIN", "平台管理员");
        LoginSession user = login("user", "123456", "USER", "周边游用户");

        ResponseEntity<Map> forbidden = exchange("/api/user/page", HttpMethod.GET, null, user.token);
        assertThat(forbidden.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertSuccess(exchange("/api/user/page", HttpMethod.GET, null, admin.token));
        assertSuccess(exchange("/api/spot/page", HttpMethod.GET, null, admin.token));
        assertSuccess(exchange("/api/dashboard/stats", HttpMethod.GET, null, admin.token));
        assertSuccess(exchange("/api/dashboard/trend", HttpMethod.GET, null, admin.token));

        assertSuccess(exchange("/api/spot/list?city=杭州", HttpMethod.GET, null, user.token));
        assertSuccess(exchange("/api/traveler", HttpMethod.POST, Map.of(
                "name", "冒烟测试出行人",
                "certType", "身份证",
                "certNo", "330102200001010088",
                "phone", "13600000000",
                "isDefault", 0
        ), user.token));
        assertSuccess(exchange("/api/traveler/page?name=冒烟测试", HttpMethod.GET, null, user.token));

        Map<String, Object> favoriteToggle = data(assertSuccess(exchange("/api/favorite/toggle", HttpMethod.POST, Map.of(
                "spotId", 2
        ), user.token)));
        assertThat(favoriteToggle.get("favored")).isEqualTo(true);
        assertSuccess(exchange("/api/favorite/page", HttpMethod.GET, null, user.token));

        Map<String, Object> order = data(assertSuccess(exchange("/api/order", HttpMethod.POST, Map.of(
                "travelerId", 1,
                "spotId", 2,
                "travelDate", "2026-05-18",
                "quantity", 2,
                "remark", "默认 H2 自举环境下的订单验证"
        ), user.token)));
        Long orderId = ((Number) order.get("id")).longValue();
        assertSuccess(exchange("/api/order/pay", HttpMethod.PUT, Map.of("id", orderId), user.token));
        assertSuccess(exchange("/api/order/finish", HttpMethod.PUT, Map.of("id", orderId), user.token));
        assertSuccess(exchange("/api/order/my-page?orderNo=" + order.get("orderNo"), HttpMethod.GET, null, user.token));
        assertSuccess(exchange("/api/order/export", HttpMethod.GET, null, user.token));

        Map<String, Object> review = data(assertSuccess(exchange("/api/review", HttpMethod.POST, Map.of(
                "orderId", orderId,
                "score", 5,
                "content", "体验很好，路线安排合理，适合周末短途。"
        ), user.token)));
        Long reviewId = ((Number) review.get("id")).longValue();
        assertSuccess(exchange("/api/review/reply", HttpMethod.PUT, Map.of(
                "id", reviewId,
                "replyContent", "感谢反馈，祝您旅途愉快。"
        ), admin.token));
        assertSuccess(exchange("/api/review/my-page", HttpMethod.GET, null, user.token));

        Map<String, Object> complaint = data(assertSuccess(exchange("/api/complaint", HttpMethod.POST, Map.of(
                "orderId", orderId,
                "type", "服务咨询",
                "content", "希望后续能增加更多集合点。",
                "attachmentUrls", "[]"
        ), user.token)));
        Long complaintId = ((Number) complaint.get("id")).longValue();
        assertSuccess(exchange("/api/complaint/handle", HttpMethod.PUT, Map.of(
                "id", complaintId,
                "handleResult", "已记录，会在后续线路中优化集合点。"
        ), admin.token));
        assertSuccess(exchange("/api/complaint/my-page", HttpMethod.GET, null, user.token));
        assertSuccess(exchange("/api/dashboard/stats", HttpMethod.GET, null, user.token));
        assertSuccess(exchange("/api/dashboard/trend", HttpMethod.GET, null, user.token));

        assertSuccess(exchange("/api/auth/logout", HttpMethod.POST, null, user.token));
        ResponseEntity<Map> afterLogout = exchange("/api/auth/info", HttpMethod.GET, null, user.token);
        assertThat(afterLogout.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private LoginSession login(String username, String password, String role, String nickname) {
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/auth/login", Map.of(
                "username", username,
                "password", password
        ), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("code")).isEqualTo(200);

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
