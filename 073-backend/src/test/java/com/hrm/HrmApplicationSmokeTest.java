package com.hrm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HrmApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SuppressWarnings("unchecked")
    void smokeTestLoginAuthorizationAttendanceAndLogout() {
        ResponseEntity<Map> anonymous = restTemplate.getForEntity("/api/user/info", Map.class);
        assertThat(anonymous.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginResult admin = login("admin", "123456");
        LoginResult hr = login("hr", "123456");
        LoginResult employee = login("zhangsan", "123456");

        assertThat(admin.user()).doesNotContainKey("password");
        assertThat(hr.user()).doesNotContainKey("password");
        assertThat(employee.user()).doesNotContainKey("password");

        ResponseEntity<Map> employeeUserList = restTemplate.exchange(
                "/api/user/list",
                HttpMethod.GET,
                authEntity(employee.token()),
                Map.class
        );
        assertThat(employeeUserList.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertOk(restTemplate.exchange("/api/user/list", HttpMethod.GET, authEntity(hr.token()), Map.class));
        assertOk(restTemplate.exchange("/api/employee/statistics", HttpMethod.GET, authEntity(hr.token()), Map.class));
        assertOk(restTemplate.exchange("/api/department/tree", HttpMethod.GET, authEntity(hr.token()), Map.class));
        assertOk(restTemplate.exchange("/api/announcement/top", HttpMethod.GET, authEntity(employee.token()), Map.class));

        assertOk(restTemplate.exchange("/api/attendance/today", HttpMethod.GET, authEntity(employee.token()), Map.class));
        assertOk(restTemplate.exchange("/api/attendance/clockIn", HttpMethod.POST, authEntity(employee.token()), Map.class));
        assertOk(restTemplate.exchange("/api/attendance/clockOut", HttpMethod.POST, authEntity(employee.token()), Map.class));

        ResponseEntity<Map> leave = restTemplate.exchange(
                "/api/leave",
                HttpMethod.POST,
                authEntity(employee.token(), Map.of(
                        "employeeId", 2,
                        "type", "annual",
                        "startDate", "2026-06-01",
                        "endDate", "2026-06-02",
                        "days", 2,
                        "reason", "family trip"
                )),
                Map.class
        );
        assertOk(leave);

        assertOk(restTemplate.exchange("/api/user/logout", HttpMethod.POST, authEntity(admin.token()), Map.class));
        ResponseEntity<Map> afterLogout = restTemplate.exchange(
                "/api/user/info",
                HttpMethod.GET,
                authEntity(admin.token()),
                Map.class
        );
        assertThat(afterLogout.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @SuppressWarnings("unchecked")
    private LoginResult login(String username, String password) {
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/user/login",
                Map.of("username", username, "password", password),
                Map.class
        );
        assertOk(response);
        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        return new LoginResult((String) data.get("token"), (Map<String, Object>) data.get("user"));
    }

    private HttpEntity<Object> authEntity(String token) {
        return authEntity(token, null);
    }

    private HttpEntity<Object> authEntity(String token, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(body, headers);
    }

    private void assertOk(ResponseEntity<Map> response) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("code")).isEqualTo(200);
    }

    private record LoginResult(String token, Map<String, Object> user) {
    }
}
