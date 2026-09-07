package com.ticket;

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
class TicketSystemApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void smokeTicketPurchasePaymentAndVerificationFlow() throws Exception {
        Map<String, Object> publicMovies = assertGetOk("/api/movie/public/list?pageNum=1&pageSize=10", null);
        assertThat(records(publicMovies)).extracting(item -> ((Number) item.get("id")).longValue()).contains(1L);

        ResponseEntity<String> anonymousStats = restTemplate.getForEntity("/api/statistics/dashboard", String.class);
        assertThat(anonymousStats.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginSession admin = login("admin", "123456", "ADMIN");
        LoginSession user = login("user", "123456", "USER");

        assertGetOk("/api/user/list?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/movie/list?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/cinema/list?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/hall/list?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/showtime/list?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/payment/list?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/ticket/list?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/comment/list?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/statistics/dashboard", admin.token());
        assertGetOk("/api/statistics/sales-trend?days=7", admin.token());
        assertGetOk("/api/statistics/box-office", admin.token());

        ResponseEntity<String> userList = exchange("/api/user/list?pageNum=1&pageSize=10", HttpMethod.GET, null, user.token());
        assertThat(userList.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        ResponseEntity<String> otherUserOrder = exchange("/api/order/2", HttpMethod.GET, null, user.token());
        assertThat(otherUserOrder.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        Map<String, Object> seatsPage = assertGetOk("/api/seat/showtime/1", user.token());
        List<Map<String, Object>> seats = castList(seatsPage);
        List<Long> seatIds = seats.stream()
                .filter(item -> "AVAILABLE".equals(item.get("status")))
                .limit(2)
                .map(item -> ((Number) item.get("id")).longValue())
                .toList();
        assertThat(seatIds).hasSize(2);

        Map<String, Object> seatPayload = Map.of("showtimeId", 1, "seatIds", seatIds);
        assertPostOk("/api/seat/lock", seatPayload, user.token());

        Map<String, Object> order = assertPostOk("/api/order/create", Map.of(
                "showtimeId", 1,
                "seatIds", seatIds,
                "couponId", 1
        ), user.token());
        long orderId = ((Number) order.get("orderId")).longValue();
        assertThat(orderId).isGreaterThan(2L);
        assertThat(String.valueOf(order.get("payAmount"))).isEqualTo("139.8");

        Map<String, Object> payment = assertPostOk("/api/payment/create", Map.of(
                "orderId", orderId,
                "payType", "BALANCE"
        ), user.token());
        assertThat(payment.get("status")).isEqualTo("WAIT");

        Map<String, Object> paid = assertPostOk("/api/payment/balance", Map.of(
                "orderId", orderId,
                "payType", "BALANCE"
        ), user.token());
        assertThat(paid.get("status")).isEqualTo("SUCCESS");

        Map<String, Object> orderDetail = assertGetOk("/api/order/" + orderId, user.token());
        assertThat(orderDetail.get("status")).isEqualTo("PAID");

        Map<String, Object> myTickets = assertGetOk("/api/ticket/my?pageNum=1&pageSize=10", user.token());
        Map<String, Object> issuedTicket = records(myTickets).stream()
                .filter(item -> ((Number) item.get("orderId")).longValue() == orderId)
                .findFirst()
                .orElseThrow();
        assertThat(issuedTicket.get("status")).isEqualTo("UNUSED");

        assertPostOk("/api/ticket/verify", Map.of("ticketNo", issuedTicket.get("ticketNo")), admin.token());

        assertPutOk("/api/order/finish/" + orderId, null, user.token());
        Map<String, Object> finishedOrder = assertGetOk("/api/order/" + orderId, user.token());
        assertThat(finishedOrder.get("status")).isEqualTo("FINISHED");

        assertPostOk("/api/comment/add", Map.of(
                "movieId", 1,
                "orderId", orderId,
                "rating", 10,
                "content", "购票流程顺畅，座位锁定和出票都正常。"
        ), user.token());
        Map<String, Object> pendingComments = assertGetOk("/api/comment/list?pageNum=1&pageSize=10&status=PENDING", admin.token());
        long commentId = records(pendingComments).stream()
                .filter(item -> "购票流程顺畅，座位锁定和出票都正常。".equals(item.get("content")))
                .map(item -> ((Number) item.get("id")).longValue())
                .findFirst()
                .orElseThrow();
        assertPutOk("/api/comment/audit/" + commentId + "/APPROVED", null, admin.token());

        ResponseEntity<String> logout = exchange("/api/auth/logout", HttpMethod.POST, Map.of(), user.token());
        assertThat(logout.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> afterLogout = exchange("/api/auth/info", HttpMethod.GET, null, user.token());
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
        ResponseEntity<String> response = token == null
                ? restTemplate.getForEntity(url, String.class)
                : exchange(url, HttpMethod.GET, null, token);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = body(response);
        assertThat(body.get("code")).isEqualTo(200);
        return dataOrValue(body.get("data"));
    }

    private Map<String, Object> assertPostOk(String url, Object payload, String token) throws Exception {
        ResponseEntity<String> response = exchange(url, HttpMethod.POST, payload, token);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = body(response);
        assertThat(body.get("code")).isEqualTo(200);
        Object data = body.get("data");
        return data == null ? Map.of() : dataOrValue(data);
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

    private Map<String, Object> dataOrValue(Object value) {
        if (value instanceof Map<?, ?>) {
            return castMap(value);
        }
        return Map.of("value", value);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castMap(Object value) {
        return (Map<String, Object>) value;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> castList(Map<String, Object> value) {
        return (List<Map<String, Object>>) value.get("value");
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> records(Map<String, Object> page) {
        return (List<Map<String, Object>>) page.get("records");
    }

    private record LoginSession(String token) {
    }
}
