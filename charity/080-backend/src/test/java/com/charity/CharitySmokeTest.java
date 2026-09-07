package com.charity;

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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CharitySmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void coreAuthPermissionAndDonationFlowWorks() {
        ResponseEntity<Map> anonymous = get("/api/child/list", null);
        assertEquals(HttpStatus.UNAUTHORIZED, anonymous.getStatusCode());

        Map<String, Object> admin = login("admin", "123456");
        Map<String, Object> donor = login("donor", "123456");
        Map<String, Object> volunteer = login("volunteer", "123456");

        String adminToken = (String) admin.get("token");
        String donorToken = (String) donor.get("token");
        String volunteerToken = (String) volunteer.get("token");
        Map<String, Object> donorInfo = castMap(donor.get("userInfo"));
        assertFalse(donorInfo.containsKey("password"), "登录响应不能泄露 password 字段");

        ResponseEntity<Map> donorInfoResp = get("/api/user/info", donorToken);
        assertEquals(HttpStatus.OK, donorInfoResp.getStatusCode());
        assertFalse(castMap(bodyData(donorInfoResp)).containsKey("password"), "当前用户接口不能泄露 password 字段");

        ResponseEntity<Map> donorStats = get("/api/statistics/dashboard", donorToken);
        assertEquals(HttpStatus.FORBIDDEN, donorStats.getStatusCode());

        ResponseEntity<Map> donorUserList = get("/api/user/list", donorToken);
        assertEquals(HttpStatus.FORBIDDEN, donorUserList.getStatusCode());

        ResponseEntity<Map> volunteerAddChild = post("/api/child/add", mapOf(
                "name", "测试儿童",
                "gender", "女",
                "province", "四川省",
                "city", "成都市",
                "sponsorStatus", 0
        ), volunteerToken);
        assertEquals(HttpStatus.OK, volunteerAddChild.getStatusCode());

        ResponseEntity<Map> donorAddChild = post("/api/child/add", mapOf("name", "越权儿童"), donorToken);
        assertEquals(HttpStatus.FORBIDDEN, donorAddChild.getStatusCode());

        ResponseEntity<Map> ownDonors = get("/api/donor/list", donorToken);
        assertEquals(HttpStatus.OK, ownDonors.getStatusCode());
        List<Map<String, Object>> donorRecords = records(ownDonors);
        assertEquals(1, donorRecords.size());
        assertEquals(1, intValue(donorRecords.get(0).get("id")));

        ResponseEntity<Map> createDonation = post("/api/donation/add", mapOf(
                "donorId", 2,
                "childId", 1,
                "amount", BigDecimal.valueOf(123.45),
                "donationType", "money",
                "paymentMethod", "alipay"
        ), donorToken);
        assertEquals(HttpStatus.OK, createDonation.getStatusCode());

        ResponseEntity<Map> donorDonations = get("/api/donation/list", donorToken);
        List<Map<String, Object>> donationRecords = records(donorDonations);
        Map<String, Object> created = donationRecords.stream()
                .filter(item -> BigDecimal.valueOf(123.45).compareTo(new BigDecimal(item.get("amount").toString())) == 0)
                .findFirst()
                .orElseThrow();
        assertEquals(1, intValue(created.get("donorId")), "捐赠人不能伪造 donorId 给他人记账");

        ResponseEntity<Map> donorConfirm = put("/api/donation/confirm/" + created.get("id"), null, donorToken);
        assertEquals(HttpStatus.FORBIDDEN, donorConfirm.getStatusCode());

        ResponseEntity<Map> adminConfirm = put("/api/donation/confirm/" + created.get("id"), null, adminToken);
        assertEquals(HttpStatus.OK, adminConfirm.getStatusCode());

        ResponseEntity<Map> volunteerSubmit = post("/api/application/submit", mapOf(
                "childId", 1,
                "applyReason", "测试资助申请",
                "requiredAmount", BigDecimal.valueOf(800)
        ), volunteerToken);
        assertEquals(HttpStatus.OK, volunteerSubmit.getStatusCode());

        ResponseEntity<Map> donorApplications = get("/api/application/list", donorToken);
        assertEquals(HttpStatus.FORBIDDEN, donorApplications.getStatusCode());

        ResponseEntity<Map> logout = post("/api/user/logout", null, donorToken);
        assertEquals(HttpStatus.OK, logout.getStatusCode());
        ResponseEntity<Map> afterLogout = get("/api/user/info", donorToken);
        assertEquals(HttpStatus.UNAUTHORIZED, afterLogout.getStatusCode());
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

    private List<Map<String, Object>> records(ResponseEntity<Map> response) {
        Map<String, Object> page = castMap(bodyData(response));
        return (List<Map<String, Object>>) page.get("records");
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
}
