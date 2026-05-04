package com.teachres;

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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeachResApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loginPermissionMaterialAndLogoutFlowWork() {
        ResponseEntity<Map> anonymousInfo = restTemplate.getForEntity(url("/api/auth/info"), Map.class);
        assertEquals(HttpStatus.UNAUTHORIZED, anonymousInfo.getStatusCode());

        ResponseEntity<Map> publicMaterials = restTemplate.getForEntity(url("/api/material/public/list?pageNum=1&pageSize=5"), Map.class);
        assertEquals(HttpStatus.OK, publicMaterials.getStatusCode());

        Map<String, Object> adminLogin = login("admin", "123456");
        Map<String, Object> teacherLogin = login("teacher", "123456");
        Map<String, Object> studentLogin = login("student", "123456");

        String adminToken = String.valueOf(adminLogin.get("token"));
        String teacherToken = String.valueOf(teacherLogin.get("token"));
        String studentToken = String.valueOf(studentLogin.get("token"));
        Map<String, Object> adminUser = asMap(adminLogin.get("userInfo"));
        assertEquals("admin", adminUser.get("role"));
        assertFalse(adminUser.containsKey("password"));

        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.GET, "/api/statistics/dashboard", adminToken, null).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, exchangeWithToken(HttpMethod.GET, "/api/statistics/dashboard", studentToken, null).getStatusCode());

        Map<String, Object> materialPayload = new LinkedHashMap<>();
        materialPayload.put("title", "教师自建资料");
        materialPayload.put("summary", "验证上传者自动绑定");
        materialPayload.put("categoryId", 1);
        materialPayload.put("grade", "2026级");
        materialPayload.put("subject", "测试课程");
        materialPayload.put("keyword", "测试");
        materialPayload.put("fileName", "teacher.pdf");
        materialPayload.put("filePath", "/upload/material/teacher.pdf");
        materialPayload.put("fileSize", 1024);
        materialPayload.put("fileType", "pdf");
        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.POST, "/api/material/add", teacherToken, materialPayload).getStatusCode());

        ResponseEntity<Map> teacherMaterials = exchangeWithToken(HttpMethod.GET, "/api/material/list?pageNum=1&pageSize=10&title=教师自建资料", teacherToken, null);
        assertEquals(HttpStatus.OK, teacherMaterials.getStatusCode());
        Map<String, Object> teacherMaterialBody = asMap(teacherMaterials.getBody());
        List<?> list = (List<?>) asMap(teacherMaterialBody.get("data")).get("list");
        Map<String, Object> firstMaterial = asMap(list.get(0));
        assertEquals(2, ((Number) firstMaterial.get("uploaderId")).intValue());

        assertEquals(HttpStatus.FORBIDDEN, exchangeWithToken(HttpMethod.PUT, "/api/audit/submit", studentToken,
                Map.of("materialId", 3, "auditStatus", 1, "auditRemark", "学生越权")).getStatusCode());
        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.PUT, "/api/audit/submit", adminToken,
                Map.of("materialId", 3, "auditStatus", 1, "auditRemark", "管理员审核通过")).getStatusCode());

        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.POST, "/api/material/download/1", studentToken, null).getStatusCode());
        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.POST, "/api/favorite/add/3", studentToken, null).getStatusCode());

        assertEquals(HttpStatus.OK, exchangeWithToken(HttpMethod.POST, "/api/auth/logout", adminToken, null).getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED, exchangeWithToken(HttpMethod.GET, "/api/auth/info", adminToken, null).getStatusCode());
    }

    private Map<String, Object> login(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(Map.of("username", username, "password", password), headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url("/api/auth/login"), entity, Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        Map<String, Object> data = asMap(body.get("data"));
        assertNotNull(data.get("token"));
        return data;
    }

    private ResponseEntity<Map> exchangeWithToken(HttpMethod method, String path, String token, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url(path), method, entity, Map.class);
    }

    private String url(String path) {
        return "http://127.0.0.1:" + port + path;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> asMap(Object value) {
        return value == null ? Map.of() : new LinkedHashMap<>((Map<String, Object>) value);
    }
}
