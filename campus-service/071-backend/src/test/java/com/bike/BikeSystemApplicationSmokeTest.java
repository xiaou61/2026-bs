package com.bike;

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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BikeSystemApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void smokeBikeRentalWalletFaultAndPermissionFlow() throws Exception {
        ResponseEntity<String> anonymousStats = restTemplate.getForEntity("/api/statistics/overview", String.class);
        assertThat(anonymousStats.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginSession admin = login("admin", "123456", "admin");
        LoginSession operator = login("operator", "123456", "operator");
        LoginSession user = login("user", "123456", "user");
        LoginSession testUser = login("test", "123456", "user");

        assertGetOk("/api/user/list?pageNum=1&pageSize=10", admin.token());
        assertGetOk("/api/bike/list?pageNum=1&pageSize=10", operator.token());
        assertGetOk("/api/station/list?pageNum=1&pageSize=10", operator.token());
        assertGetOk("/api/statistics/overview", admin.token());
        assertGetOk("/api/statistics/ride-trend?days=7", admin.token());
        assertGetOk("/api/statistics/income-trend?days=7", admin.token());

        ResponseEntity<String> userList = exchange("/api/user/list?pageNum=1&pageSize=10", HttpMethod.GET, null, user.token());
        assertThat(userList.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        ResponseEntity<String> otherRide = exchange("/api/ride/1", HttpMethod.GET, null, testUser.token());
        assertThat(otherRide.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        List<Map<String, Object>> stations = listData(assertGetOk("/api/station/all", user.token()));
        assertThat(stations).extracting(item -> ((Number) item.get("id")).longValue()).contains(1L, 2L);

        List<Map<String, Object>> availableBikes = listData(assertGetOk("/api/bike/available/1", user.token()));
        assertThat(availableBikes).extracting(item -> ((Number) item.get("id")).longValue()).contains(1L);

        Map<String, Object> started = assertPostOk("/api/ride/start", Map.of("bikeId", 1, "stationId", 1), user.token());
        long rideId = ((Number) started.get("id")).longValue();
        assertThat(started.get("status")).isEqualTo(1);

        Map<String, Object> currentRide = assertGetOk("/api/ride/current", user.token());
        assertThat(((Number) currentRide.get("id")).longValue()).isEqualTo(rideId);

        Map<String, Object> ended = assertPostOk("/api/ride/end", Map.of("endStationId", 2), user.token());
        assertThat(ended.get("status")).isEqualTo(3);
        assertThat(ended.get("payStatus")).isEqualTo(1);
        assertThat(new BigDecimal(String.valueOf(ended.get("amount")))).isGreaterThanOrEqualTo(new BigDecimal("1.50"));

        Map<String, Object> rideDetail = assertGetOk("/api/ride/" + rideId, user.token());
        assertThat(((Number) rideDetail.get("userId")).longValue()).isEqualTo(3L);

        Map<String, Object> wallet = assertGetOk("/api/wallet/records?pageNum=1&pageSize=10", user.token());
        assertThat(pageItems(wallet)).extracting(item -> String.valueOf(item.get("description"))).anyMatch(text -> text.contains("骑行扣费"));

        assertPostOk("/api/fault", Map.of(
                "bikeId", 7,
                "type", 1,
                "description", "测试上报车锁异常"
        ), user.token());
        Map<String, Object> pendingFaults = assertGetOk("/api/fault/list?pageNum=1&pageSize=10&status=0", operator.token());
        long faultId = pageItems(pendingFaults).stream()
                .filter(item -> "测试上报车锁异常".equals(item.get("description")))
                .map(item -> ((Number) item.get("id")).longValue())
                .findFirst()
                .orElseThrow();
        assertPutOk("/api/fault/handle/" + faultId, Map.of("status", 2, "handleResult", "已完成锁具复位"), operator.token());

        assertPostOk("/api/credit/adjust", Map.of("userId", 3, "scoreChange", -5, "description", "测试信用扣分"), admin.token());
        Object score = valueData(assertGetOk("/api/credit/score", user.token()));
        assertThat(((Number) score).intValue()).isEqualTo(97);

        ResponseEntity<String> logout = exchange("/api/user/logout", HttpMethod.POST, Map.of(), user.token());
        assertThat(logout.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> afterLogout = exchange("/api/user/info", HttpMethod.GET, null, user.token());
        assertThat(afterLogout.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private LoginSession login(String username, String password, String expectedRole) throws Exception {
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/user/login",
                jsonEntity(Map.of("username", username, "password", password), null),
                String.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = body(response);
        assertThat(body.get("code")).isEqualTo(200);

        Map<String, Object> data = castMap(body.get("data"));
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
        return dataOrValue(body.get("data"));
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

    private Map<String, Object> dataOrValue(Object value) {
        if (value == null) {
            return Map.of();
        }
        if (value instanceof Map<?, ?>) {
            return castMap(value);
        }
        return Map.of("value", value);
    }

    private Object valueData(Map<String, Object> data) {
        return data.get("value");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castMap(Object value) {
        return (Map<String, Object>) value;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> listData(Map<String, Object> data) {
        return (List<Map<String, Object>>) data.get("value");
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> pageItems(Map<String, Object> page) {
        return (List<Map<String, Object>>) page.get("list");
    }

    private record LoginSession(String token) {
    }
}
