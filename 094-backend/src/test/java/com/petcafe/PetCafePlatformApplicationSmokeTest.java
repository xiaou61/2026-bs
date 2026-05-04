package com.petcafe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetCafePlatformApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void smokeFlow() {
        assertEquals(HttpStatus.UNAUTHORIZED, restTemplate.getForEntity(url("/api/auth/info"), Map.class).getStatusCode());
        assertEquals(HttpStatus.OK, restTemplate.getForEntity(url("/api/pet/public/list"), Map.class).getStatusCode());
        assertEquals(HttpStatus.OK, restTemplate.getForEntity(url("/api/menu/public/list"), Map.class).getStatusCode());
        assertEquals(HttpStatus.OK, restTemplate.getForEntity(url("/api/notice/public/list"), Map.class).getStatusCode());

        LoginResult admin = login("admin", "123456");
        LoginResult customer = login("customer", "123456");
        LoginResult staff = login("staff", "123456");

        assertEquals("ADMIN", ((Map<?, ?>) admin.body().get("user")).get("role"));
        assertFalse(((Map<?, ?>) admin.body().get("user")).containsKey("password"));

        assertEquals(HttpStatus.FORBIDDEN,
                exchange("/api/statistics/dashboard", HttpMethod.GET, null, customer.token(), Map.class).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN,
                exchange("/api/notice/list", HttpMethod.GET, null, staff.token(), Map.class).getStatusCode());

        ResponseEntity<Map> createOrder = exchange("/api/order/create", HttpMethod.POST,
                "{\"shopId\":1,\"menuId\":1,\"quantity\":1,\"dineType\":\"IN_STORE\"}", customer.token(), Map.class);
        assertEquals(HttpStatus.OK, createOrder.getStatusCode());
        Map<?, ?> createData = (Map<?, ?>) createOrder.getBody().get("data");
        assertNotNull(createData);
        Number orderId = (Number) createData.get("orderId");
        assertNotNull(orderId);

        ResponseEntity<Map> pay = exchange("/api/payment/balance", HttpMethod.POST,
                "{\"orderId\":" + orderId.longValue() + "}", customer.token(), Map.class);
        assertEquals(HttpStatus.OK, pay.getStatusCode());
        assertEquals(200, pay.getBody().get("code"));

        assertEquals(HttpStatus.FORBIDDEN,
                exchange("/api/order/create", HttpMethod.POST,
                        "{\"shopId\":1,\"menuId\":1,\"quantity\":1,\"dineType\":\"IN_STORE\"}", staff.token(), Map.class).getStatusCode());

        assertEquals(HttpStatus.OK, exchange("/api/auth/logout", HttpMethod.POST, "{}", customer.token(), Map.class).getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED,
                exchange("/api/order/" + orderId.longValue(), HttpMethod.GET, null, customer.token(), Map.class).getStatusCode());
    }

    private LoginResult login(String username, String password) {
        ResponseEntity<Map> response = exchange("/api/auth/login", HttpMethod.POST,
                "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}", null, Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().get("code"));
        Map<?, ?> data = (Map<?, ?>) response.getBody().get("data");
        assertNotNull(data);
        Object token = data.get("token");
        assertTrue(token instanceof String && !((String) token).isBlank());
        return new LoginResult((String) token, data);
    }

    private <T> ResponseEntity<T> exchange(String path, HttpMethod method, String body, String token, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.setBearerAuth(token);
        }
        return restTemplate.exchange(url(path), method, new HttpEntity<>(body, headers), responseType);
    }

    private String url(String path) {
        return "http://127.0.0.1:" + port + path;
    }

    private record LoginResult(String token, Map<?, ?> body) {
    }
}
