package com.cinema;

import com.cinema.common.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CinemaMemberApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void memberFlowPermissionAndLogoutShouldWork() {
        ResponseEntity<Result<Object>> anonInfo = exchange("/api/auth/info", HttpMethod.GET, null, new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, anonInfo.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> publicMovies = exchange("/api/movie/public/list?pageNum=1&pageSize=10", HttpMethod.GET, null, new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, publicMovies.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> publicShowtimes = exchange("/api/showtime/public/list?pageNum=1&pageSize=10", HttpMethod.GET, null, new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, publicShowtimes.getStatusCode());

        LoginResult admin = login("admin", "123456");
        LoginResult staff = login("staff", "123456");
        LoginResult member = login("member", "123456");
        LoginResult member2 = login("member2", "123456");

        assertFalse(((Map<?, ?>) member.data.get("user")).containsKey("password"));

        ResponseEntity<Result<Map<String, Object>>> memberStats = exchange("/api/statistics/dashboard", HttpMethod.GET, bearer(member.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.FORBIDDEN, memberStats.getStatusCode());

        ResponseEntity<Result<List<Map<String, Object>>>> seatsResponse = exchange("/api/seat/showtime/1", HttpMethod.GET, bearer(member.token), new ParameterizedTypeReference<Result<List<Map<String, Object>>>>() {});
        assertEquals(HttpStatus.OK, seatsResponse.getStatusCode());
        List<Map<String, Object>> seats = seatsResponse.getBody().getData();
        assertNotNull(seats);
        Long seatId1 = ((Number) seats.get(0).get("id")).longValue();
        Long seatId2 = ((Number) seats.get(1).get("id")).longValue();

        ResponseEntity<Result<Object>> lock = exchange("/api/seat/lock", HttpMethod.POST, jsonBearer(member.token, Map.of("showtimeId", 1, "seatIds", List.of(seatId1, seatId2))), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, lock.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> createOrder = exchange("/api/order/create", HttpMethod.POST, jsonBearer(member.token, Map.of(
                "showtimeId", 1,
                "seatIds", List.of(seatId1, seatId2),
                "couponId", 1
        )), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, createOrder.getStatusCode());
        Long orderId = ((Number) createOrder.getBody().getData().get("orderId")).longValue();

        ResponseEntity<Result<Object>> staffCreateOrder = exchange("/api/order/create", HttpMethod.POST, jsonBearer(staff.token, Map.of(
                "showtimeId", 1,
                "seatIds", List.of(seatId1)
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, staffCreateOrder.getStatusCode());

        ResponseEntity<Result<Object>> pay = exchange("/api/payment/balance", HttpMethod.POST, jsonBearer(member.token, Map.of("orderId", orderId)), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, pay.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> memberOrders = exchange("/api/order/my?pageNum=1&pageSize=10", HttpMethod.GET, bearer(member.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, memberOrders.getStatusCode());

        ResponseEntity<Result<Object>> crossOrder = exchange("/api/order/" + orderId, HttpMethod.GET, bearer(member2.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, crossOrder.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> tickets = exchange("/api/ticket/my?pageNum=1&pageSize=10", HttpMethod.GET, bearer(member.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, tickets.getStatusCode());
        List<Map<String, Object>> ticketRecords = (List<Map<String, Object>>) tickets.getBody().getData().get("records");
        assertNotNull(ticketRecords);
        Long ticketId = ((Number) ticketRecords.stream()
                .filter(item -> ((Number) item.get("orderId")).longValue() == orderId)
                .findFirst()
                .orElseThrow()
                .get("id")).longValue();

        ResponseEntity<Result<Object>> crossTicket = exchange("/api/ticket/" + ticketId, HttpMethod.GET, bearer(member2.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, crossTicket.getStatusCode());

        ResponseEntity<Result<Object>> fakeComment = exchange("/api/comment/add", HttpMethod.POST, jsonBearer(member2.token, Map.of(
                "movieId", 1,
                "orderId", 1,
                "rating", 8,
                "content", "未购票也评论"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, fakeComment.getStatusCode());

        ResponseEntity<Result<Object>> addComment = exchange("/api/comment/add", HttpMethod.POST, jsonBearer(member.token, Map.of(
                "movieId", 1,
                "orderId", orderId,
                "rating", 9,
                "content", "新下单后观影期待很高"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, addComment.getStatusCode());

        ResponseEntity<Result<Object>> memberVerify = exchange("/api/ticket/verify", HttpMethod.POST, jsonBearer(member.token, Map.of("ticketNo", "ET202605010001")), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, memberVerify.getStatusCode());

        ResponseEntity<Result<Object>> staffVerify = exchange("/api/ticket/verify", HttpMethod.POST, jsonBearer(staff.token, Map.of("ticketNo", "ET202605010001")), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, staffVerify.getStatusCode());

        ResponseEntity<Result<Object>> logout = exchange("/api/auth/logout", HttpMethod.POST, bearer(member.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, logout.getStatusCode());

        ResponseEntity<Result<Object>> afterLogout = exchange("/api/order/my?pageNum=1&pageSize=10", HttpMethod.GET, bearer(member.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, afterLogout.getStatusCode());
    }

    private LoginResult login(String username, String password) {
        ResponseEntity<Result<Map<String, Object>>> response = exchange("/api/auth/login", HttpMethod.POST, json(Map.of("username", username, "password", password)), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        return new LoginResult(String.valueOf(response.getBody().getData().get("token")), response.getBody().getData());
    }

    private HttpEntity<Map<String, Object>> json(Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<Map<String, Object>> jsonBearer(String token, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<Void> bearer(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    private <T> ResponseEntity<T> exchange(String path, HttpMethod method, HttpEntity<?> entity, ParameterizedTypeReference<T> type) {
        HttpEntity<?> actualEntity = entity == null ? new HttpEntity<>(new HttpHeaders()) : entity;
        return restTemplate.exchange("http://127.0.0.1:" + port + path, method, actualEntity, type);
    }

    private record LoginResult(String token, Map<String, Object> data) {}
}
