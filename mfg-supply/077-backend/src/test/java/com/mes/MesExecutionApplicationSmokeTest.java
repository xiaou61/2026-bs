package com.mes;

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
class MesExecutionApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SuppressWarnings("unchecked")
    void smokeTestLoginAuthorizationWorkOrderReviewComplaintAndLogout() {
        ResponseEntity<Map> anonymous = restTemplate.getForEntity("/api/auth/info", Map.class);
        assertThat(anonymous.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginResult admin = login("admin");
        LoginResult planner1 = login("planner1");
        LoginResult planner2 = login("planner2");
        LoginResult operator = login("operator1");

        assertThat(admin.user()).doesNotContainKey("password");
        assertThat(planner1.user()).doesNotContainKey("password");
        assertThat(planner2.user()).doesNotContainKey("password");
        assertThat(operator.user()).doesNotContainKey("password");

        ResponseEntity<Map> operatorDashboard = restTemplate.exchange(
                "/api/dashboard/stats",
                HttpMethod.GET,
                authEntity(operator.token()),
                Map.class
        );
        assertThat(operatorDashboard.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertOk(restTemplate.exchange("/api/dashboard/stats", HttpMethod.GET, authEntity(admin.token()), Map.class));
        assertOk(restTemplate.exchange("/api/item/list?pageSize=5", HttpMethod.GET, authEntity(operator.token()), Map.class));
        assertOk(restTemplate.exchange("/api/category/list", HttpMethod.GET, authEntity(operator.token()), Map.class));
        assertOk(restTemplate.exchange("/api/announcement/list", HttpMethod.GET, authEntity(operator.token()), Map.class));

        ResponseEntity<Map> orderResponse = restTemplate.exchange(
                "/api/order",
                HttpMethod.POST,
                authEntity(operator.token(), Map.of(
                        "serviceId", 3,
                        "quantity", 1,
                        "remark", "test mes work order",
                        "appointmentDate", LocalDate.now().plusDays(2).toString(),
                        "appointmentTime", "10:30",
                        "plateNo", "BATCH-077-001",
                        "vehicleModel", "CTRL-M90",
                        "faultDesc", "总装后进行通电老化测试"
                )),
                Map.class
        );
        assertOk(orderResponse);
        Number orderId = (Number) ((Map<String, Object>) orderResponse.getBody().get("data")).get("id");

        assertOk(restTemplate.exchange("/api/order/pay/" + orderId.longValue(), HttpMethod.PUT, authEntity(operator.token()), Map.class));

        ResponseEntity<Map> wrongPlannerDeliver = restTemplate.exchange(
                "/api/order/deliver/" + orderId.longValue(),
                HttpMethod.PUT,
                authEntity(planner1.token()),
                Map.class
        );
        assertThat(wrongPlannerDeliver.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertOk(restTemplate.exchange("/api/order/deliver/" + orderId.longValue(), HttpMethod.PUT, authEntity(planner2.token()), Map.class));
        assertOk(restTemplate.exchange("/api/order/complete/" + orderId.longValue(), HttpMethod.PUT, authEntity(operator.token()), Map.class));
        assertOk(restTemplate.exchange(
                "/api/review",
                HttpMethod.POST,
                authEntity(operator.token(), Map.of("orderId", orderId.longValue(), "rating", 5, "content", "工单执行稳定，流转记录清晰")),
                Map.class
        ));
        assertOk(restTemplate.exchange("/api/favorite/3", HttpMethod.POST, authEntity(operator.token()), Map.class));
        assertOk(restTemplate.exchange(
                "/api/complaint",
                HttpMethod.POST,
                authEntity(operator.token(), Map.of("orderId", orderId.longValue(), "type", "ORDER", "content", "需要补充首件报告")),
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
