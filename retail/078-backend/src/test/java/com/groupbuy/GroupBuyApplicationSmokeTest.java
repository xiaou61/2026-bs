package com.groupbuy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GroupBuyApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void coreGroupBuyFlowAndPermissionsWork() {
        assertStatus(HttpStatus.UNAUTHORIZED, get("/api/auth/info", null));

        Map<String, Object> admin = login("admin", 0);
        Map<String, Object> merchant = login("merchant", 1);
        Map<String, Object> merchant2 = login("merchant2", 1);
        Map<String, Object> user = login("user", 2);
        Map<String, Object> user2 = login("user2", 2);

        assertStatus(HttpStatus.FORBIDDEN, get("/api/stats/overview", token(user)));
        assertOk(get("/api/stats/overview", token(admin)));

        assertOk(get("/api/category/list", null));
        assertOk(get("/api/activity/home", null));
        assertOk(get("/api/product/detail/1", null));
        assertOk(get("/api/notice/front", null));

        Map<String, Object> cartBody = new HashMap<>();
        cartBody.put("productId", 1);
        cartBody.put("activityId", 1);
        cartBody.put("quantity", 1);
        assertOk(post("/api/cart/add", cartBody, token(user)));

        Map<String, Object> cartList = assertOk(get("/api/cart/list", token(user)));
        List<Map<String, Object>> carts = castList(cartList.get("data"));
        assertFalse(carts.isEmpty());
        Number cartId = (Number) carts.get(0).get("id");

        Map<String, Object> orderBody = new HashMap<>();
        orderBody.put("cartIds", Arrays.asList(cartId.longValue()));
        orderBody.put("addressId", 1);
        orderBody.put("remark", "smoke test");
        Map<String, Object> orderCreate = assertOk(post("/api/order/create", orderBody, token(user)));
        String orderNo = String.valueOf(orderCreate.get("data"));

        Map<String, Object> orderPage = assertOk(get("/api/order/page?orderNo=" + orderNo, token(user)));
        Map<String, Object> pageData = castMap(orderPage.get("data"));
        List<Map<String, Object>> orders = castList(pageData.get("records"));
        assertFalse(orders.isEmpty());
        Long orderId = ((Number) orders.get(0).get("id")).longValue();

        assertStatus(HttpStatus.FORBIDDEN, put("/api/order/pay/" + orderId, null, token(user2)));
        assertOk(put("/api/order/pay/" + orderId, null, token(user)));
        assertStatus(HttpStatus.FORBIDDEN, put("/api/order/ship/" + orderId, null, token(merchant2)));
        assertOk(put("/api/order/ship/" + orderId, null, token(merchant)));
        assertStatus(HttpStatus.FORBIDDEN, put("/api/order/receive/" + orderId, null, token(user2)));
        assertOk(put("/api/order/receive/" + orderId, null, token(user)));

        Map<String, Object> reviewBody = new HashMap<>();
        reviewBody.put("orderId", orderId);
        reviewBody.put("rating", 5);
        reviewBody.put("content", "团购流程自动化测试评价");
        reviewBody.put("images", "[]");
        assertOk(post("/api/review/add", reviewBody, token(user)));
        assertOk(get("/api/review/page", token(admin)));

        assertOk(post("/api/auth/logout", null, token(admin)));
        assertStatus(HttpStatus.UNAUTHORIZED, get("/api/auth/info", token(admin)));
    }

    private Map<String, Object> login(String username, int expectedRole) {
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", "123456");
        Map<String, Object> response = assertOk(post("/api/auth/login", body, null));
        Map<String, Object> data = castMap(response.get("data"));
        assertTrue(data.containsKey("token"));
        Map<String, Object> user = castMap(data.get("user"));
        assertEquals(expectedRole, ((Number) user.get("role")).intValue());
        assertFalse(user.containsKey("password"));
        return data;
    }

    private String token(Map<String, Object> loginData) {
        return String.valueOf(loginData.get("token"));
    }

    private Map<String, Object> assertOk(ResponseEntity<Map> response) {
        assertStatus(HttpStatus.OK, response);
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, ((Number) body.get("code")).intValue());
        return body;
    }

    private void assertStatus(HttpStatus status, ResponseEntity<Map> response) {
        assertEquals(status, response.getStatusCode());
    }

    private ResponseEntity<Map> get(String url, String token) {
        return exchange(url, HttpMethod.GET, null, token);
    }

    private ResponseEntity<Map> post(String url, Object body, String token) {
        return exchange(url, HttpMethod.POST, body, token);
    }

    private ResponseEntity<Map> put(String url, Object body, String token) {
        return exchange(url, HttpMethod.PUT, body, token);
    }

    private ResponseEntity<Map> exchange(String url, HttpMethod method, Object body, String token) {
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.setBearerAuth(token);
        }
        return restTemplate.exchange(url, method, new HttpEntity<>(body, headers), Map.class);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castMap(Object value) {
        return (Map<String, Object>) value;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> castList(Object value) {
        return (List<Map<String, Object>>) value;
    }
}
