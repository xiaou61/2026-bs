package com.vending;

import com.vending.common.PageResult;
import com.vending.common.Result;
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
class VendingManagementApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void permissionAndPaymentFlowShouldWork() {
        ResponseEntity<Result<Object>> anonInfo = exchange("/api/auth/info", HttpMethod.GET, null, new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, anonInfo.getStatusCode());

        ResponseEntity<Result<List<Map<String, Object>>>> publicMachines = exchange("/api/machine/public/list", HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Map<String, Object>>>>() {});
        assertEquals(HttpStatus.OK, publicMachines.getStatusCode());
        assertEquals(2, publicMachines.getBody().getData().size());

        ResponseEntity<Result<List<Map<String, Object>>>> publicProducts = exchange("/api/product/public/list", HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Map<String, Object>>>>() {});
        assertEquals(HttpStatus.OK, publicProducts.getStatusCode());
        assertEquals(6, publicProducts.getBody().getData().size());

        ResponseEntity<Result<List<Map<String, Object>>>> publicNotices = exchange("/api/notice/public/list", HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Map<String, Object>>>>() {});
        assertEquals(HttpStatus.OK, publicNotices.getStatusCode());
        assertEquals(2, publicNotices.getBody().getData().size());

        LoginResult admin = login("admin", "123456");
        LoginResult staff = login("staff", "123456");
        LoginResult customer = login("customer", "123456");

        assertFalse(((Map<?, ?>) admin.data.get("user")).containsKey("password"));

        ResponseEntity<Result<Map<String, Object>>> register = exchange("/api/auth/register", HttpMethod.POST, json(Map.of(
                "username", "customer_test",
                "password", "123456",
                "nickname", "顾客小王",
                "phone", "13800009999"
        )), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, register.getStatusCode());
        assertFalse(register.getBody().getData().containsKey("password"));

        LoginResult customer2 = login("customer_test", "123456");

        ResponseEntity<Result<Object>> customerStats = exchange("/api/statistics/dashboard", HttpMethod.GET, bearer(customer.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, customerStats.getStatusCode());

        ResponseEntity<Result<Object>> adminStats = exchange("/api/statistics/dashboard", HttpMethod.GET, bearer(admin.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, adminStats.getStatusCode());

        ResponseEntity<Result<Object>> staffCreateOrder = exchange("/api/order/create", HttpMethod.POST, jsonBearer(staff.token, Map.of(
                "machineId", 1,
                "slotId", 1,
                "quantity", 1
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, staffCreateOrder.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> createOrder = exchange("/api/order/create", HttpMethod.POST, jsonBearer(customer.token, Map.of(
                "machineId", 1,
                "slotId", 1,
                "quantity", 1
        )), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, createOrder.getStatusCode());
        Long orderId = ((Number) createOrder.getBody().getData().get("orderId")).longValue();

        ResponseEntity<Result<Object>> crossOrder = exchange("/api/order/" + orderId, HttpMethod.GET, bearer(customer2.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, crossOrder.getStatusCode());

        ResponseEntity<Result<Object>> staffRecharge = exchange("/api/payment/recharge", HttpMethod.POST, jsonBearer(staff.token, Map.of(
                "amount", 50
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, staffRecharge.getStatusCode());

        ResponseEntity<Result<Object>> balancePay = exchange("/api/payment/balance", HttpMethod.POST, jsonBearer(customer.token, Map.of(
                "orderId", orderId
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, balancePay.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> orderDetail = exchange("/api/order/" + orderId, HttpMethod.GET, bearer(customer.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, orderDetail.getStatusCode());
        assertEquals("SHIPPED", orderDetail.getBody().getData().get("status"));

        ResponseEntity<Result<PageResult<Map<String, Object>>>> shipments = exchange("/api/shipment/list?pageNum=1&pageSize=20", HttpMethod.GET, bearer(customer.token), new ParameterizedTypeReference<Result<PageResult<Map<String, Object>>>>() {});
        assertEquals(HttpStatus.OK, shipments.getStatusCode());
        assertEquals(4L, shipments.getBody().getData().getTotal());

        ResponseEntity<Result<PageResult<Map<String, Object>>>> payments = exchange("/api/payment/list?pageNum=1&pageSize=20", HttpMethod.GET, bearer(customer.token), new ParameterizedTypeReference<Result<PageResult<Map<String, Object>>>>() {});
        assertEquals(HttpStatus.OK, payments.getStatusCode());
        assertEquals(4L, payments.getBody().getData().getTotal());

        ResponseEntity<Result<Object>> fakeFault = exchange("/api/fault/add", HttpMethod.POST, jsonBearer(customer2.token, Map.of(
                "orderId", orderId,
                "machineId", 1,
                "reportType", "测试越权",
                "content", "伪造他人订单故障"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, fakeFault.getStatusCode());

        ResponseEntity<Result<Object>> ownFault = exchange("/api/fault/add", HttpMethod.POST, jsonBearer(customer.token, Map.of(
                "orderId", orderId,
                "machineId", 1,
                "reportType", "出货问题",
                "content", "测试本人订单故障提交"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, ownFault.getStatusCode());

        ResponseEntity<Result<Object>> logout = exchange("/api/auth/logout", HttpMethod.POST, bearer(customer.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, logout.getStatusCode());

        ResponseEntity<Result<Object>> afterLogout = exchange("/api/order/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(customer.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, afterLogout.getStatusCode());
    }

    private LoginResult login(String username, String password) {
        ResponseEntity<Result<Map<String, Object>>> response = exchange("/api/auth/login", HttpMethod.POST, json(Map.of(
                "username", username,
                "password", password
        )), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
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

    private record LoginResult(String token, Map<String, Object> data) {
    }
}
