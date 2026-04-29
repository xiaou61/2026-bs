package com.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryManagementApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void inventoryMainFlowsShouldWorkWithDefaultH2Profile() {
        ResponseEntity<Map> unauthenticated = restTemplate.getForEntity("/api/dashboard/stats", Map.class);
        assertThat(unauthenticated.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginSession admin = login("admin", "123456", "ADMIN", "系统管理员");
        LoginSession staff = login("staff", "123456", "STAFF", "采购专员");

        ResponseEntity<Map> forbidden = exchange("/api/user/page", HttpMethod.GET, null, staff.token);
        assertThat(forbidden.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertSuccess(exchange("/api/user/page", HttpMethod.GET, null, admin.token));
        assertSuccess(exchange("/api/dashboard/stats", HttpMethod.GET, null, staff.token));
        assertSuccess(exchange("/api/supplier/list", HttpMethod.GET, null, staff.token));
        assertSuccess(exchange("/api/customer/list", HttpMethod.GET, null, staff.token));
        assertSuccess(exchange("/api/category/list", HttpMethod.GET, null, staff.token));
        assertSuccess(exchange("/api/product/list", HttpMethod.GET, null, staff.token));

        assertSuccess(exchange("/api/announcement", HttpMethod.POST, Map.of(
                "title", "冒烟测试公告",
                "content", "默认 H2 自举环境可正常发布公告",
                "status", 1
        ), admin.token));

        Integer stockBeforePurchase = productStock(3L, staff.token);
        assertSuccess(exchange("/api/purchase", HttpMethod.POST, Map.of(
                "supplierId", 1,
                "productId", 3,
                "quantity", 7,
                "price", new BigDecimal("118.50"),
                "remark", "冒烟测试采购入库"
        ), staff.token));
        Long purchaseId = firstId(data(assertSuccess(exchange("/api/purchase/page?status=0", HttpMethod.GET, null, staff.token))));
        assertSuccess(exchange("/api/purchase/approve/" + purchaseId, HttpMethod.PUT, null, staff.token));
        assertThat(productStock(3L, staff.token)).isEqualTo(stockBeforePurchase + 7);

        Integer stockBeforeSale = productStock(3L, staff.token);
        assertSuccess(exchange("/api/sale", HttpMethod.POST, Map.of(
                "customerId", 1,
                "productId", 3,
                "quantity", 3,
                "price", new BigDecimal("168.00"),
                "remark", "冒烟测试销售出库"
        ), staff.token));
        Long saleId = firstId(data(assertSuccess(exchange("/api/sale/page?status=0", HttpMethod.GET, null, staff.token))));
        assertSuccess(exchange("/api/sale/approve/" + saleId, HttpMethod.PUT, null, staff.token));
        assertThat(productStock(3L, staff.token)).isEqualTo(stockBeforeSale - 3);
        assertSuccess(exchange("/api/stock/page", HttpMethod.GET, null, staff.token));

        assertSuccess(exchange("/api/sale", HttpMethod.POST, Map.of(
                "customerId", 1,
                "productId", 2,
                "quantity", 999,
                "price", new BigDecimal("1799.00"),
                "remark", "冒烟测试库存不足"
        ), staff.token));
        Long insufficientSaleId = firstId(data(assertSuccess(exchange("/api/sale/page?status=0", HttpMethod.GET, null, staff.token))));
        ResponseEntity<Map> insufficient = exchange("/api/sale/approve/" + insufficientSaleId, HttpMethod.PUT, null, staff.token);
        assertThat(insufficient.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(insufficient.getBody()).isNotNull();
        assertThat(insufficient.getBody().get("message").toString()).contains("库存不足");

        assertSuccess(exchange("/api/auth/logout", HttpMethod.POST, null, admin.token));
        ResponseEntity<Map> afterLogout = exchange("/api/auth/info", HttpMethod.GET, null, admin.token);
        assertThat(afterLogout.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private LoginSession login(String username, String password, String role, String nickname) {
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/auth/login", Map.of(
                "username", username,
                "password", password
        ), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("code")).isEqualTo(200);

        Map<String, Object> data = data(response);
        assertThat(data.get("token")).isInstanceOf(String.class);
        Map<String, Object> user = castMap(data.get("user"));
        assertThat(user.get("role")).isEqualTo(role);
        assertThat(user.get("nickname")).isEqualTo(nickname);
        assertThat(user).doesNotContainKey("password");
        return new LoginSession((String) data.get("token"));
    }

    private Integer productStock(Long productId, String token) {
        Map<String, Object> page = data(assertSuccess(exchange("/api/product/page?pageNum=1&pageSize=20", HttpMethod.GET, null, token)));
        List<?> records = (List<?>) page.get("records");
        for (Object record : records) {
            Map<String, Object> item = castMap(record);
            if (((Number) item.get("id")).longValue() == productId) {
                return ((Number) item.get("stock")).intValue();
            }
        }
        throw new AssertionError("商品不存在: " + productId);
    }

    private ResponseEntity<Map> exchange(String path, HttpMethod method, Object body, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return restTemplate.exchange(path, method, new HttpEntity<>(body, headers), Map.class);
    }

    private ResponseEntity<Map> assertSuccess(ResponseEntity<Map> response) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("code")).isEqualTo(200);
        return response;
    }

    private Map<String, Object> data(ResponseEntity<Map> response) {
        return castMap(response.getBody().get("data"));
    }

    private Long firstId(Map<String, Object> page) {
        List<?> records = (List<?>) page.get("records");
        assertThat(records).isNotEmpty();
        Map<String, Object> first = castMap(records.get(0));
        return ((Number) first.get("id")).longValue();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castMap(Object value) {
        return (Map<String, Object>) value;
    }

    private static class LoginSession {
        private final String token;

        private LoginSession(String token) {
            this.token = token;
        }
    }
}
