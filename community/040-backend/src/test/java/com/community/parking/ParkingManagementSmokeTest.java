package com.community.parking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingManagementSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void defaultH2RuntimeShouldServeCoreApis() {
        ResponseEntity<Map<String, Object>> loginResponse = restTemplate.exchange(
                "/api/auth/login",
                HttpMethod.POST,
                new HttpEntity<>(Map.of("username", "admin", "password", "123456")),
                new ParameterizedTypeReference<>() {
                });

        assertThat(loginResponse.getStatusCode().is2xxSuccessful()).isTrue();
        Map<String, Object> loginBody = loginResponse.getBody();
        assertThat(loginBody).isNotNull();
        assertThat(loginBody.get("code")).isEqualTo(200);

        @SuppressWarnings("unchecked")
        Map<String, Object> loginData = (Map<String, Object>) loginBody.get("data");
        assertThat(loginData.get("token")).asString().isNotBlank();

        ResponseEntity<Map<String, Object>> typeResponse = restTemplate.exchange(
                "/api/violation-types",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                });
        assertThat(typeResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(typeResponse.getBody()).isNotNull();
        assertThat(typeResponse.getBody().get("code")).isEqualTo(200);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> types = (List<Map<String, Object>>) typeResponse.getBody().get("data");
        assertThat(types).extracting(type -> type.get("name")).contains("占用消防通道");

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.add("userId", "2");
        ResponseEntity<Map<String, Object>> reportResponse = restTemplate.exchange(
                "/api/reports",
                HttpMethod.POST,
                new HttpEntity<>(Map.of(
                        "plateNumber", "京D45678",
                        "violationTypeId", 1,
                        "location", "C栋出入口",
                        "longitude", 116.397328,
                        "latitude", 39.916727,
                        "description", "车辆堵塞小区出入口",
                        "images", "",
                        "isAnonymous", false
                ), userHeaders),
                new ParameterizedTypeReference<>() {
                });
        assertThat(reportResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(reportResponse.getBody()).isNotNull();
        assertThat(reportResponse.getBody().get("code")).isEqualTo(200);

        @SuppressWarnings("unchecked")
        Map<String, Object> report = (Map<String, Object>) reportResponse.getBody().get("data");
        Number reportId = (Number) report.get("id");
        assertThat(report.get("status")).isEqualTo("PENDING");

        HttpHeaders adminHeaders = new HttpHeaders();
        adminHeaders.add("userId", "1");
        ResponseEntity<Map<String, Object>> auditResponse = restTemplate.exchange(
                "/api/reports/" + reportId.longValue() + "/audit?status=APPROVED&reason=证据清晰",
                HttpMethod.POST,
                new HttpEntity<>(adminHeaders),
                new ParameterizedTypeReference<>() {
                });
        assertThat(auditResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(auditResponse.getBody()).isNotNull();
        assertThat(auditResponse.getBody().get("code")).isEqualTo(200);
    }
}
