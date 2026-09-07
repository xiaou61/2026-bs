package com.railway;

import com.railway.common.Result;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RailwayTicketApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void bookingPermissionAfterSaleAndLogoutShouldWork() {
        ResponseEntity<Result<Object>> anonInfo = exchange("/api/auth/info", HttpMethod.GET, null, new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, anonInfo.getStatusCode());

        ResponseEntity<Result<List<Object>>> publicSchedules = exchange("/api/schedule/enabled", HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Object>>>() {});
        assertEquals(HttpStatus.OK, publicSchedules.getStatusCode());
        assertEquals(3, publicSchedules.getBody().getData().size());

        ResponseEntity<Result<List<Object>>> publicNotice = exchange("/api/notice/public/list", HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Object>>>() {});
        assertEquals(HttpStatus.OK, publicNotice.getStatusCode());
        assertEquals(2, publicNotice.getBody().getData().size());

        LoginResult admin = login("admin", "123456");
        LoginResult dispatcher = login("dispatcher", "123456");
        LoginResult user = login("user", "123456");
        LoginResult user2 = login("user2", "123456");

        assertFalse(((Map<?, ?>) user.data.get("user")).containsKey("password"));

        ResponseEntity<Result<Map<String, Object>>> adminStats = exchange("/api/statistics/dashboard", HttpMethod.GET, bearer(admin.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, adminStats.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> userStats = exchange("/api/statistics/dashboard", HttpMethod.GET, bearer(user.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.FORBIDDEN, userStats.getStatusCode());

        ResponseEntity<Result<List<Map<String, Object>>>> seats = exchange("/api/seat/schedule/2", HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Map<String, Object>>>>() {});
        assertEquals(HttpStatus.OK, seats.getStatusCode());
        List<Map<String, Object>> seatList = seats.getBody().getData();
        assertFalse(seatList.isEmpty());
        Long seatId = ((Number) seatList.stream().filter(item -> "AVAILABLE".equals(item.get("status"))).findFirst().orElseThrow().get("id")).longValue();

        Map<String, Object> lockBody = new HashMap<>();
        lockBody.put("scheduleId", 2);
        lockBody.put("seatIds", List.of(seatId));
        ResponseEntity<Result<Object>> lock = exchange("/api/seat/lock", HttpMethod.POST, jsonBearer(user.token, lockBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, lock.getStatusCode());

        Map<String, Object> orderBody = new HashMap<>();
        orderBody.put("scheduleId", 2);
        orderBody.put("seatIds", List.of(seatId));
        orderBody.put("passengerIds", List.of(1));
        orderBody.put("contactPhone", "13800000003");
        ResponseEntity<Result<Map<String, Object>>> createOrder = exchange("/api/order/create", HttpMethod.POST, jsonBearer(user.token, orderBody), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, createOrder.getStatusCode());
        Long orderId = ((Number) createOrder.getBody().getData().get("orderId")).longValue();

        Map<String, Object> payBody = new HashMap<>();
        payBody.put("orderId", orderId);
        payBody.put("payType", "BALANCE");
        ResponseEntity<Result<Object>> balancePay = exchange("/api/payment/balance", HttpMethod.POST, jsonBearer(user.token, payBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, balancePay.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> myTickets = exchange("/api/ticket/my?pageNum=1&pageSize=10", HttpMethod.GET, bearer(user.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, myTickets.getStatusCode());
        List<Map<String, Object>> ticketRecords = (List<Map<String, Object>>) myTickets.getBody().getData().get("records");
        Map<String, Object> createdTicket = ticketRecords.stream()
                .filter(item -> item.get("orderId") != null && ((Number) item.get("orderId")).longValue() == orderId)
                .findFirst()
                .orElse(null);
        assertNotNull(createdTicket);

        Long ticketId = ((Number) createdTicket.get("id")).longValue();
        String ticketNo = String.valueOf(createdTicket.get("ticketNo"));

        ResponseEntity<Result<Object>> crossOrder = exchange("/api/order/" + orderId, HttpMethod.GET, bearer(user2.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, crossOrder.getStatusCode());

        ResponseEntity<Result<Object>> crossTicket = exchange("/api/ticket/" + ticketId, HttpMethod.GET, bearer(user2.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, crossTicket.getStatusCode());

        Map<String, Object> afterSaleBody = new HashMap<>();
        afterSaleBody.put("orderId", orderId);
        afterSaleBody.put("serviceType", "REFUND");
        afterSaleBody.put("reason", "临时有事");
        ResponseEntity<Result<Object>> applyAfterSale = exchange("/api/after-sale/apply", HttpMethod.POST, jsonBearer(user.token, afterSaleBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, applyAfterSale.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> myAfterSale = exchange("/api/after-sale/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(user.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, myAfterSale.getStatusCode());
        List<Map<String, Object>> afterSaleRecords = (List<Map<String, Object>>) myAfterSale.getBody().getData().get("records");
        Map<String, Object> createdAfterSale = afterSaleRecords.stream()
                .filter(item -> item.get("orderId") != null && ((Number) item.get("orderId")).longValue() == orderId)
                .findFirst()
                .orElse(null);
        assertNotNull(createdAfterSale);

        Map<String, Object> reviewBody = new HashMap<>();
        reviewBody.put("reviewStatus", "APPROVED");
        reviewBody.put("reviewRemark", "通过");
        ResponseEntity<Result<Object>> review = exchange("/api/after-sale/review/" + createdAfterSale.get("id"), HttpMethod.PUT, jsonBearer(dispatcher.token, reviewBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, review.getStatusCode());

        Map<String, Object> verifyBody = new HashMap<>();
        verifyBody.put("ticketNo", ticketNo);
        ResponseEntity<Result<Object>> verifyAsUser = exchange("/api/ticket/verify", HttpMethod.POST, jsonBearer(user.token, verifyBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, verifyAsUser.getStatusCode());

        ResponseEntity<Result<Object>> logout = exchange("/api/auth/logout", HttpMethod.POST, bearer(user.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, logout.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> afterLogout = exchange("/api/ticket/my?pageNum=1&pageSize=10", HttpMethod.GET, bearer(user.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, afterLogout.getStatusCode());
    }

    private LoginResult login(String username, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        ResponseEntity<Result<Map<String, Object>>> response = exchange("/api/auth/login", HttpMethod.POST, json(body), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getCode());
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
