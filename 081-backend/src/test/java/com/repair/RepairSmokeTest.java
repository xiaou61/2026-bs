package com.repair;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RepairSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void authPermissionAndRepairOrderFlowWorks() {
        assertEquals(HttpStatus.UNAUTHORIZED, get("/api/user/info", null).getStatusCode());
        assertEquals(HttpStatus.OK, get("/api/category/public/list", null).getStatusCode());

        Map<String, Object> admin = login("admin", "123456");
        Map<String, Object> tech = login("tech1", "123456");
        Map<String, Object> customer = login("user1", "123456");
        Map<String, Object> otherCustomer = login("user2", "123456");

        String adminToken = (String) admin.get("token");
        String techToken = (String) tech.get("token");
        String customerToken = (String) customer.get("token");
        String otherCustomerToken = (String) otherCustomer.get("token");
        assertFalse(castMap(admin.get("userInfo")).containsKey("password"));

        assertEquals(HttpStatus.FORBIDDEN, get("/api/statistics/dashboard", customerToken).getStatusCode());
        assertEquals(HttpStatus.OK, get("/api/statistics/dashboard", adminToken).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, get("/api/user/list", customerToken).getStatusCode());

        ResponseEntity<Map> createOrder = post("/api/order/user/create", mapOf(
                "userId", 1,
                "contactName", "冒烟用户",
                "contactPhone", "13900000000",
                "province", "四川省",
                "city", "成都市",
                "district", "高新区",
                "address", "天府软件园",
                "categoryId", 1,
                "brand", "格力",
                "model", "KFR-35GW",
                "faultDesc", "空调不制冷"
        ), customerToken);
        assertEquals(HttpStatus.OK, createOrder.getStatusCode());
        Long orderId = longValue(castMap(bodyData(createOrder)).get("orderId"));

        assertEquals(HttpStatus.FORBIDDEN, get("/api/order/user/detail/" + orderId, otherCustomerToken).getStatusCode());
        assertEquals(HttpStatus.OK, get("/api/order/list", adminToken).getStatusCode());

        ResponseEntity<Map> assign = put("/api/order/assign", mapOf("orderId", orderId, "technicianId", 1), adminToken);
        assertEquals(HttpStatus.OK, assign.getStatusCode());
        assertEquals(HttpStatus.OK, get("/api/order/technician/list", techToken).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, get("/api/process/list/" + orderId, otherCustomerToken).getStatusCode());

        ResponseEntity<Map> techStatus = put("/api/order/technician/status", mapOf(
                "orderId", orderId,
                "status", 3,
                "content", "检测完成，待用户支付"
        ), techToken);
        assertEquals(HttpStatus.OK, techStatus.getStatusCode());

        assertEquals(HttpStatus.OK, put("/api/order/user/pay/" + orderId, mapOf("content", "用户完成支付"), customerToken).getStatusCode());
        assertEquals(HttpStatus.OK, post("/api/evaluate/user/submit", mapOf(
                "orderId", orderId,
                "score", 5,
                "attitudeScore", 5,
                "qualityScore", 5,
                "speedScore", 5,
                "content", "服务很好"
        ), customerToken).getStatusCode());

        assertEquals(HttpStatus.OK, post("/api/user/logout", null, customerToken).getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED, get("/api/user/info", customerToken).getStatusCode());
    }

    private Map<String, Object> login(String username, String password) {
        ResponseEntity<Map> response = post("/api/user/login", mapOf("username", username, "password", password), null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, intValue(response.getBody().get("code")));
        return castMap(response.getBody().get("data"));
    }

    private ResponseEntity<Map> get(String path, String token) {
        return restTemplate.exchange(url(path), HttpMethod.GET, new HttpEntity<>(headers(token)), Map.class);
    }

    private ResponseEntity<Map> post(String path, Map<String, Object> body, String token) {
        return restTemplate.exchange(url(path), HttpMethod.POST, new HttpEntity<>(body, headers(token)), Map.class);
    }

    private ResponseEntity<Map> put(String path, Map<String, Object> body, String token) {
        return restTemplate.exchange(url(path), HttpMethod.PUT, new HttpEntity<>(body, headers(token)), Map.class);
    }

    private HttpHeaders headers(String token) {
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.setBearerAuth(token);
        }
        return headers;
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    private Object bodyData(ResponseEntity<Map> response) {
        assertNotNull(response.getBody());
        return response.getBody().get("data");
    }

    private Map<String, Object> mapOf(Object... values) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < values.length; i += 2) {
            map.put(values[i].toString(), values[i + 1]);
        }
        return map;
    }

    private Map<String, Object> castMap(Object value) {
        return (Map<String, Object>) value;
    }

    private int intValue(Object value) {
        return ((Number) value).intValue();
    }

    private Long longValue(Object value) {
        return ((Number) value).longValue();
    }
}
