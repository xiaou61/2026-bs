package com.harbin.tourism;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TourismApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SuppressWarnings("unchecked")
    void smokeTestCoreTourismFlowAndAuthorization() {
        ResponseEntity<Map> anonymous = restTemplate.getForEntity("/api/user/info", Map.class);
        assertThat(anonymous.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginResult admin = login("admin", "123456");
        LoginResult user = login("user", "123456");
        LoginResult tourist = login("tourist", "123456");

        assertThat(admin.user()).doesNotContainKey("password");
        assertThat(user.user()).doesNotContainKey("password");

        assertOk(restTemplate.getForEntity("/api/spots/list", Map.class));
        assertOk(restTemplate.getForEntity("/api/routes/list", Map.class));
        assertOk(restTemplate.getForEntity("/api/hotels/list", Map.class));
        assertOk(restTemplate.getForEntity("/api/restaurants/list", Map.class));
        assertOk(restTemplate.getForEntity("/api/activities/list", Map.class));
        assertOk(restTemplate.getForEntity("/api/announcements/list", Map.class));

        ResponseEntity<Map> userAdminAccess = restTemplate.exchange(
                "/api/admin/statistics",
                HttpMethod.GET,
                authEntity(user.token()),
                Map.class
        );
        assertThat(userAdminAccess.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        ResponseEntity<Map> adminStats = restTemplate.exchange(
                "/api/admin/statistics",
                HttpMethod.GET,
                authEntity(admin.token()),
                Map.class
        );
        assertOk(adminStats);

        ResponseEntity<Map> orderResponse = restTemplate.exchange(
                "/api/tickets/order",
                HttpMethod.POST,
                authEntity(user.token(), Map.of(
                        "spotId", 1,
                        "ticketTypeId", 1,
                        "ticketDate", "2026-12-20",
                        "quantity", 1
                )),
                Map.class
        );
        assertOk(orderResponse);
        Map<String, Object> order = (Map<String, Object>) orderResponse.getBody().get("data");
        Number orderId = (Number) order.get("id");

        ResponseEntity<Map> touristOrderAccess = restTemplate.exchange(
                "/api/tickets/orders/" + orderId,
                HttpMethod.GET,
                authEntity(tourist.token()),
                Map.class
        );
        assertThat(touristOrderAccess.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        ResponseEntity<Map> refundResponse = restTemplate.exchange(
                "/api/tickets/orders/" + orderId + "/refund",
                HttpMethod.POST,
                authEntity(user.token()),
                Map.class
        );
        assertOk(refundResponse);

        assertOk(restTemplate.exchange(
                "/api/user/favorite",
                HttpMethod.POST,
                authEntity(user.token(), Map.of("targetId", 2, "targetType", "spot")),
                Map.class
        ));
        assertOk(restTemplate.exchange(
                "/api/reviews",
                HttpMethod.POST,
                authEntity(user.token(), Map.of("targetId", 1, "targetType", "spot", "rating", 4, "content", "Nice trip")),
                Map.class
        ));
        assertOk(restTemplate.exchange(
                "/api/activities/1/register",
                HttpMethod.POST,
                authEntity(user.token()),
                Map.class
        ));

        assertOk(restTemplate.exchange(
                "/api/auth/logout",
                HttpMethod.POST,
                authEntity(user.token()),
                Map.class
        ));
        ResponseEntity<Map> afterLogout = restTemplate.exchange(
                "/api/user/info",
                HttpMethod.GET,
                authEntity(user.token()),
                Map.class
        );
        assertThat(afterLogout.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @SuppressWarnings("unchecked")
    private LoginResult login(String username, String password) {
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/auth/login",
                Map.of("username", username, "password", password),
                Map.class
        );
        assertOk(response);
        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        return new LoginResult((String) data.get("token"), (Map<String, Object>) data.get("user"));
    }

    private HttpEntity<Object> authEntity(String token) {
        return authEntity(token, null);
    }

    private HttpEntity<Object> authEntity(String token, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(body, headers);
    }

    private void assertOk(ResponseEntity<Map> response) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("code")).isEqualTo(200);
    }

    private record LoginResult(String token, Map<String, Object> user) {
    }
}
