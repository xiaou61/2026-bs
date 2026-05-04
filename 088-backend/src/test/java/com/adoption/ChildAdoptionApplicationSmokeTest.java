package com.adoption;

import com.adoption.common.Result;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChildAdoptionApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loginPermissionOwnershipAndLogoutShouldWork() {
        ResponseEntity<Result<Object>> anonInfo = exchange("/api/auth/info", HttpMethod.GET, null, new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, anonInfo.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> publicChild = exchange("/api/child/public/list?pageNum=1&pageSize=5", HttpMethod.GET, null, new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, publicChild.getStatusCode());
        assertEquals(2, ((Number) publicChild.getBody().getData().get("total")).intValue());

        LoginResult admin = login("admin", "123456");
        LoginResult reviewer = login("reviewer", "123456");
        LoginResult applicant = login("applicant", "123456");

        Map<String, Object> registerBody = new HashMap<>();
        registerBody.put("username", "smoke_applicant");
        registerBody.put("password", "123456");
        registerBody.put("realName", "冒烟申请人");
        registerBody.put("phone", "13812345678");
        ResponseEntity<Result<Object>> register = exchange("/api/auth/register", HttpMethod.POST, json(registerBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, register.getStatusCode());

        LoginResult applicant2 = login("smoke_applicant", "123456");

        assertFalse(((Map<?, ?>) admin.data.get("userInfo")).containsKey("password"));

        ResponseEntity<Result<Map<String, Object>>> adminStats = exchange("/api/statistics/dashboard", HttpMethod.GET, bearer(admin.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, adminStats.getStatusCode());

        Map<String, Object> illegalApplicationBody = new HashMap<>();
        illegalApplicationBody.put("childId", 3);
        illegalApplicationBody.put("reason", "reviewer cannot apply");
        illegalApplicationBody.put("expectedContactDate", "2026-05-10");
        ResponseEntity<Result<Object>> reviewerAddApplication = exchange("/api/application/add", HttpMethod.POST, jsonBearer(reviewer.token, illegalApplicationBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, reviewerAddApplication.getStatusCode());

        Map<String, Object> profileBody = new HashMap<>();
        profileBody.put("address", "南京市建邺区江东中路66号");
        profileBody.put("adoptionReason", "本人材料补充");
        profileBody.put("realName", "冒烟申请人");
        profileBody.put("phone", "13812345678");
        ResponseEntity<Result<Object>> updateProfile = exchange("/api/user/profile/update", HttpMethod.PUT, jsonBearer(applicant2.token, profileBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, updateProfile.getStatusCode());

        Map<String, Object> applicationBody = new HashMap<>();
        applicationBody.put("childId", 3);
        applicationBody.put("reason", "希望提供稳定家庭");
        applicationBody.put("expectedContactDate", "2026-05-10");
        ResponseEntity<Result<Object>> addApplication = exchange("/api/application/add", HttpMethod.POST, jsonBearer(applicant2.token, applicationBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, addApplication.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> myList = exchange("/api/application/my/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(applicant2.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, myList.getStatusCode());
        Map<?, ?> createdApplication = ((java.util.List<Map<?, ?>>) myList.getBody().getData().get("list")).stream()
                .filter(item -> ((Number) item.get("childId")).longValue() == 3L && "希望提供稳定家庭".equals(item.get("reason")))
                .findFirst()
                .orElse(null);
        assertNotNull(createdApplication);

        ResponseEntity<Result<Object>> review = exchange("/api/application/review?applicationId=" + createdApplication.get("id") + "&reviewStatus=1&reviewRemark=材料齐全",
                HttpMethod.PUT, bearer(reviewer.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, review.getStatusCode());

        Map<String, Object> crossMaterialBody = new HashMap<>();
        crossMaterialBody.put("applicationId", createdApplication.get("id"));
        crossMaterialBody.put("materialType", "身份证明");
        crossMaterialBody.put("materialName", "越权材料");
        crossMaterialBody.put("fileUrl", "https://example.com/cross.jpg");
        ResponseEntity<Result<Object>> crossMaterial = exchange("/api/application/material/add", HttpMethod.POST, jsonBearer(applicant.token, crossMaterialBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, crossMaterial.getStatusCode());

        Map<String, Object> ownMaterialBody = new HashMap<>();
        ownMaterialBody.put("applicationId", createdApplication.get("id"));
        ownMaterialBody.put("materialType", "身份证明");
        ownMaterialBody.put("materialName", "本人材料");
        ownMaterialBody.put("fileUrl", "https://example.com/self.jpg");
        ResponseEntity<Result<Object>> ownMaterial = exchange("/api/application/material/add", HttpMethod.POST, jsonBearer(applicant2.token, ownMaterialBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, ownMaterial.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> applicantAgreementList = exchange("/api/agreement/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(applicant.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.FORBIDDEN, applicantAgreementList.getStatusCode());

        ResponseEntity<Result<Object>> logout = exchange("/api/auth/logout", HttpMethod.POST, bearer(applicant2.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, logout.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> afterLogout = exchange("/api/application/my/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(applicant2.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, afterLogout.getStatusCode());
    }

    private LoginResult login(String username, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        ResponseEntity<Result<Map<String, Object>>> response = exchange("/api/auth/login", HttpMethod.POST, json(body), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getCode());
        return new LoginResult((String) response.getBody().getData().get("token"), response.getBody().getData());
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
