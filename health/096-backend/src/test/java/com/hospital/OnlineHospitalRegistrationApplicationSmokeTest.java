package com.hospital;

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
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OnlineHospitalRegistrationApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void smokeFlow() {
        assertEquals(HttpStatus.UNAUTHORIZED, restTemplate.getForEntity(url("/api/auth/info"), Map.class).getStatusCode());
        assertOk(restTemplate.getForEntity(url("/api/notice/public/list"), Map.class));
        assertOk(restTemplate.getForEntity(url("/api/banner/public/list"), Map.class));
        assertOk(restTemplate.getForEntity(url("/api/department/enabled"), Map.class));
        assertOk(restTemplate.getForEntity(url("/api/doctor/public/list"), Map.class));
        assertOk(restTemplate.getForEntity(url("/api/schedule/public/list"), Map.class));
        assertOk(restTemplate.getForEntity(url("/api/statistics/hot-doctor"), Map.class));

        LoginResult admin = login("admin", "123456");
        LoginResult doctor = login("doctor01", "123456");
        LoginResult patient = login("patient01", "123456");
        LoginResult otherPatient = login("patient02", "123456");

        Map<?, ?> adminUser = (Map<?, ?>) admin.body().get("user");
        assertEquals("ADMIN", adminUser.get("role"));
        assertFalse(adminUser.containsKey("password"));

        assertEquals(HttpStatus.FORBIDDEN,
                exchange("/api/user/page?pageNum=1&pageSize=10", HttpMethod.GET, null, patient.token(), Map.class).getStatusCode());
        assertOk(exchange("/api/statistics/dashboard", HttpMethod.GET, null, admin.token(), Map.class));
        assertOk(exchange("/api/statistics/department-rank", HttpMethod.GET, null, admin.token(), Map.class));
        assertOk(exchange("/api/statistics/appointment-trend", HttpMethod.GET, null, admin.token(), Map.class));

        ResponseEntity<Map> create = exchange("/api/appointment/create", HttpMethod.POST,
                "{\"scheduleId\":2,\"cardId\":1,\"remark\":\"smoke registration\"}", patient.token(), Map.class);
        assertOk(create);
        Map<?, ?> appointmentData = data(create);
        Number appointmentId = assertNumber(appointmentData.get("appointmentId"));
        Number orderId = assertNumber(appointmentData.get("orderId"));

        assertEquals(HttpStatus.FORBIDDEN,
                exchange("/api/order/pay/" + orderId.longValue(), HttpMethod.PUT, "{}", otherPatient.token(), Map.class).getStatusCode());
        assertOk(exchange("/api/order/pay/" + orderId.longValue(), HttpMethod.PUT, "{}", patient.token(), Map.class));
        assertOk(exchange("/api/appointment/finish/" + appointmentId.longValue(), HttpMethod.PUT, "{}", doctor.token(), Map.class));
        assertOk(exchange("/api/review/save", HttpMethod.POST,
                "{\"appointmentId\":" + appointmentId.longValue() + ",\"rating\":5,\"content\":\"smoke review\"}",
                patient.token(), Map.class));

        assertOk(exchange("/api/auth/logout", HttpMethod.POST, "{}", patient.token(), Map.class));
        assertEquals(HttpStatus.UNAUTHORIZED,
                exchange("/api/card/my", HttpMethod.GET, null, patient.token(), Map.class).getStatusCode());
    }

    private LoginResult login(String username, String password) {
        ResponseEntity<Map> response = exchange("/api/auth/login", HttpMethod.POST,
                "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}", null, Map.class);
        assertOk(response);
        Map<?, ?> data = data(response);
        Object token = data.get("token");
        assertTrue(token instanceof String && !((String) token).isBlank());
        return new LoginResult((String) token, data);
    }

    private void assertOk(ResponseEntity<Map> response) {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().get("code"));
    }

    private Map<?, ?> data(ResponseEntity<Map> response) {
        assertNotNull(response.getBody());
        Object data = response.getBody().get("data");
        assertInstanceOf(Map.class, data);
        return (Map<?, ?>) data;
    }

    private Number assertNumber(Object value) {
        assertInstanceOf(Number.class, value);
        return (Number) value;
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
