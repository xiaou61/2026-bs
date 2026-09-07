package com.xiaou.snack.wms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SnackWarehouseApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loginAndFetchSkuList() {
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(
                "/auth/login",
                Map.of("username", "admin", "password", "admin"),
                Map.class
        );

        assertThat(loginResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(loginResponse.getBody()).isNotNull();
        assertThat(loginResponse.getBody()).containsEntry("code", 200);

        Object data = loginResponse.getBody().get("data");
        assertThat(data).isInstanceOf(Map.class);
        String token = (String) ((Map<?, ?>) data).get("token");
        assertThat(token).isNotBlank();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ResponseEntity<Map> skuResponse = restTemplate.exchange(
                "/sku",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        assertThat(skuResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(skuResponse.getBody()).isNotNull();
        assertThat(skuResponse.getBody()).containsEntry("code", 200);
        assertThat(skuResponse.getBody().get("data")).isInstanceOf(List.class);
        assertThat((List<?>) skuResponse.getBody().get("data")).isNotEmpty();
    }
}
