package com.folksong.platform;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FolksongPlatformSmokeTest {

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
        String token = (String) loginData.get("token");
        assertThat(token).isNotBlank();

        ResponseEntity<Map<String, Object>> categoriesResponse = restTemplate.exchange(
                "/api/categories",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                });
        assertThat(categoriesResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(categoriesResponse.getBody()).isNotNull();
        assertThat(categoriesResponse.getBody().get("code")).isEqualTo(200);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> categories = (List<Map<String, Object>>) categoriesResponse.getBody().get("data");
        assertThat(categories).extracting(category -> category.get("name")).contains("山歌");

        ResponseEntity<Map<String, Object>> songsResponse = restTemplate.exchange(
                "/api/songs?pageNum=1&pageSize=5",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                });
        assertThat(songsResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(songsResponse.getBody()).isNotNull();
        assertThat(songsResponse.getBody().get("code")).isEqualTo(200);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ResponseEntity<Map<String, Object>> adminResponse = restTemplate.exchange(
                "/api/admin/users?pageNum=1&pageSize=5",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });
        assertThat(adminResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(adminResponse.getBody()).isNotNull();
        assertThat(adminResponse.getBody().get("code")).isEqualTo(200);
    }
}
