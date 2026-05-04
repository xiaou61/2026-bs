package com.opera;

import com.opera.common.Result;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OperaCultureApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void permissionOwnershipAndLogoutShouldWork() {
        ResponseEntity<Result<Object>> anonInfo = exchange("/api/auth/info", HttpMethod.GET, null, new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, anonInfo.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> publicRepertoire = exchange("/api/repertoire/list?pageNum=1&pageSize=10", HttpMethod.GET, null, new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, publicRepertoire.getStatusCode());

        ResponseEntity<Result<List<Object>>> publicNotice = exchange("/api/notice/public/list", HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Object>>>() {});
        assertEquals(HttpStatus.OK, publicNotice.getStatusCode());
        assertEquals(2, publicNotice.getBody().getData().size());

        LoginResult admin = login("admin", "123456");
        LoginResult artist = login("artist", "123456");
        LoginResult member = login("member", "123456");
        LoginResult member2 = login("member2", "123456");

        assertFalse(((Map<?, ?>) member.data.get("userInfo")).containsKey("password"));

        ResponseEntity<Result<Map<String, Object>>> adminStats = exchange("/api/statistics/dashboard", HttpMethod.GET, bearer(admin.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, adminStats.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> memberStats = exchange("/api/statistics/dashboard", HttpMethod.GET, bearer(member.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.FORBIDDEN, memberStats.getStatusCode());

        ResponseEntity<Result<Object>> artistBooking = exchange("/api/booking/select", HttpMethod.POST, jsonBearer(artist.token, Map.of("scheduleId", 3)), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, artistBooking.getStatusCode());

        ResponseEntity<Result<Object>> memberBooking = exchange("/api/booking/select", HttpMethod.POST, jsonBearer(member.token, Map.of("scheduleId", 3)), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, memberBooking.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> memberBookings = exchange("/api/booking/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(member.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, memberBookings.getStatusCode());
        List<Map<String, Object>> bookingRecords = (List<Map<String, Object>>) memberBookings.getBody().getData().get("list");
        Map<String, Object> createdBooking = bookingRecords.stream()
                .filter(item -> item.get("scheduleId") != null && ((Number) item.get("scheduleId")).longValue() == 3L)
                .findFirst()
                .orElse(null);
        assertNotNull(createdBooking);

        Long selectionId = ((Number) createdBooking.get("id")).longValue();

        Map<String, Object> resourceBody = new HashMap<>();
        resourceBody.put("scheduleId", 3);
        resourceBody.put("courseId", 3);
        resourceBody.put("title", "黄梅戏研学手册");
        resourceBody.put("resourceType", "pdf");
        resourceBody.put("resourceUrl", "/resource/opera/hmx-study.pdf");
        resourceBody.put("contentDesc", "研学用资料");
        ResponseEntity<Result<Object>> addResource = exchange("/api/resource/add", HttpMethod.POST, jsonBearer(artist.token, resourceBody), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, addResource.getStatusCode());

        ResponseEntity<Result<Map<String, Object>>> artistResources = exchange("/api/resource/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(artist.token), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, artistResources.getStatusCode());
        List<Map<String, Object>> resourceRecords = (List<Map<String, Object>>) artistResources.getBody().getData().get("list");
        Map<String, Object> createdResource = resourceRecords.stream()
                .filter(item -> "黄梅戏研学手册".equals(item.get("title")))
                .findFirst()
                .orElse(null);
        assertNotNull(createdResource);

        Long resourceId = ((Number) createdResource.get("id")).longValue();

        ResponseEntity<Result<Object>> adminUpdateResource = exchange("/api/resource/update", HttpMethod.PUT, jsonBearer(admin.token, Map.of(
                "id", resourceId,
                "scheduleId", 3,
                "courseId", 3,
                "title", "黄梅戏研学手册-修订版",
                "resourceType", "pdf",
                "resourceUrl", "/resource/opera/hmx-study-v2.pdf",
                "contentDesc", "管理员修订"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, adminUpdateResource.getStatusCode());

        ResponseEntity<Result<Object>> memberDeleteResource = exchange("/api/resource/delete/" + resourceId, HttpMethod.DELETE, bearer(member.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, memberDeleteResource.getStatusCode());

        ResponseEntity<Result<Object>> review = exchange("/api/review/save", HttpMethod.POST, jsonBearer(artist.token, Map.of(
                "selectionId", selectionId,
                "usualScore", 91,
                "examScore", 94,
                "remark", "表现优秀"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, review.getStatusCode());

        ResponseEntity<Result<Object>> crossReview = exchange("/api/review/save", HttpMethod.POST, jsonBearer(member.token, Map.of(
                "selectionId", selectionId,
                "usualScore", 60,
                "examScore", 60
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, crossReview.getStatusCode());

        ResponseEntity<Result<Object>> memberComment = exchange("/api/comment/add", HttpMethod.POST, jsonBearer(member.token, Map.of(
                "scheduleId", 3,
                "evaluationScore", 96,
                "evaluationContent", "研学体验很好"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, memberComment.getStatusCode());

        ResponseEntity<Result<Object>> member2Comment = exchange("/api/comment/add", HttpMethod.POST, jsonBearer(member2.token, Map.of(
                "scheduleId", 3,
                "evaluationScore", 85,
                "evaluationContent", "未预约也评论"
        )), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.FORBIDDEN, member2Comment.getStatusCode());

        ResponseEntity<Result<Object>> logout = exchange("/api/auth/logout", HttpMethod.POST, bearer(member.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.OK, logout.getStatusCode());

        ResponseEntity<Result<Object>> afterLogout = exchange("/api/booking/list?pageNum=1&pageSize=10", HttpMethod.GET, bearer(member.token), new ParameterizedTypeReference<Result<Object>>() {});
        assertEquals(HttpStatus.UNAUTHORIZED, afterLogout.getStatusCode());
    }

    private LoginResult login(String username, String password) {
        ResponseEntity<Result<Map<String, Object>>> response = exchange("/api/auth/login", HttpMethod.POST, json(Map.of("username", username, "password", password)), new ParameterizedTypeReference<Result<Map<String, Object>>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
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

    private record LoginResult(String token, Map<String, Object> data) {}
}
