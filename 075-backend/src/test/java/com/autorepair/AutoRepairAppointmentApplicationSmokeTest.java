package com.autorepair;

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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AutoRepairAppointmentApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SuppressWarnings("unchecked")
    void smokeTestLoginAuthorizationAppointmentReviewComplaintAndLogout() {
        ResponseEntity<Map> anonymous = restTemplate.getForEntity("/api/auth/info", Map.class);
        assertThat(anonymous.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginResult admin = login("admin");
        LoginResult seller1 = login("seller1");
        LoginResult seller2 = login("seller2");
        LoginResult buyer = login("buyer1");

        assertThat(admin.user()).doesNotContainKey("password");
        assertThat(seller1.user()).doesNotContainKey("password");
        assertThat(seller2.user()).doesNotContainKey("password");
        assertThat(buyer.user()).doesNotContainKey("password");

        ResponseEntity<Map> buyerDashboard = restTemplate.exchange(
                "/api/dashboard/stats",
                HttpMethod.GET,
                authEntity(buyer.token()),
                Map.class
        );
        assertThat(buyerDashboard.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertOk(restTemplate.exchange("/api/dashboard/stats", HttpMethod.GET, authEntity(admin.token()), Map.class));
        assertOk(restTemplate.exchange("/api/item/list?pageSize=5", HttpMethod.GET, authEntity(buyer.token()), Map.class));
        assertOk(restTemplate.exchange("/api/category/list", HttpMethod.GET, authEntity(buyer.token()), Map.class));
        assertOk(restTemplate.exchange("/api/announcement/list", HttpMethod.GET, authEntity(buyer.token()), Map.class));

        ResponseEntity<Map> orderResponse = restTemplate.exchange(
                "/api/order",
                HttpMethod.POST,
                authEntity(buyer.token(), Map.of(
                        "serviceId", 3,
                        "quantity", 1,
                        "remark", "test booking",
                        "appointmentDate", LocalDate.now().plusDays(2).toString(),
                        "appointmentTime", "10:30",
                        "plateNo", "沪A75T01",
                        "vehicleModel", "Tesla Model 3",
                        "faultDesc", "brake noise"
                )),
                Map.class
        );
        assertOk(orderResponse);
        Number orderId = (Number) ((Map<String, Object>) orderResponse.getBody().get("data")).get("id");

        assertOk(restTemplate.exchange("/api/order/pay/" + orderId.longValue(), HttpMethod.PUT, authEntity(buyer.token()), Map.class));

        ResponseEntity<Map> wrongSellerDeliver = restTemplate.exchange(
                "/api/order/deliver/" + orderId.longValue(),
                HttpMethod.PUT,
                authEntity(seller1.token()),
                Map.class
        );
        assertThat(wrongSellerDeliver.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertOk(restTemplate.exchange("/api/order/deliver/" + orderId.longValue(), HttpMethod.PUT, authEntity(seller2.token()), Map.class));
        assertOk(restTemplate.exchange("/api/order/complete/" + orderId.longValue(), HttpMethod.PUT, authEntity(buyer.token()), Map.class));
        assertOk(restTemplate.exchange(
                "/api/review",
                HttpMethod.POST,
                authEntity(buyer.token(), Map.of("orderId", orderId.longValue(), "rating", 5, "content", "excellent repair service")),
                Map.class
        ));
        assertOk(restTemplate.exchange("/api/favorite/3", HttpMethod.POST, authEntity(buyer.token()), Map.class));
        assertOk(restTemplate.exchange(
                "/api/complaint",
                HttpMethod.POST,
                authEntity(buyer.token(), Map.of("orderId", orderId.longValue(), "type", "ORDER", "content", "need invoice")),
                Map.class
        ));
        assertOk(restTemplate.exchange("/api/complaint/page", HttpMethod.GET, authEntity(admin.token()), Map.class));

        assertOk(restTemplate.exchange("/api/auth/logout", HttpMethod.POST, authEntity(admin.token()), Map.class));
        ResponseEntity<Map> afterLogout = restTemplate.exchange(
                "/api/auth/info",
                HttpMethod.GET,
                authEntity(admin.token()),
                Map.class
        );
        assertThat(afterLogout.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @SuppressWarnings("unchecked")
    private LoginResult login(String username) {
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/auth/login",
                Map.of("username", username, "password", "123456"),
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
