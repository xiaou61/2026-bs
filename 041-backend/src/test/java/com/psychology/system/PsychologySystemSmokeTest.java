package com.psychology.system;

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
class PsychologySystemSmokeTest {

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
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) loginData.get("user");
        assertThat(user.get("password")).isNull();

        ResponseEntity<Map<String, Object>> scalesResponse = restTemplate.exchange(
                "/api/assessments/scales",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                });
        assertThat(scalesResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(scalesResponse.getBody()).isNotNull();
        assertThat(scalesResponse.getBody().get("code")).isEqualTo(200);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> scales = (List<Map<String, Object>>) scalesResponse.getBody().get("data");
        assertThat(scales).extracting(scale -> scale.get("name")).contains("焦虑自评量表(SAS)");

        ResponseEntity<Map<String, Object>> questionsResponse = restTemplate.exchange(
                "/api/assessments/scales/1/questions",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                });
        assertThat(questionsResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(questionsResponse.getBody()).isNotNull();
        assertThat(questionsResponse.getBody().get("code")).isEqualTo(200);

        ResponseEntity<Map<String, Object>> counselorsResponse = restTemplate.exchange(
                "/api/counselors",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                });
        assertThat(counselorsResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(counselorsResponse.getBody()).isNotNull();
        assertThat(counselorsResponse.getBody().get("code")).isEqualTo(200);

        ResponseEntity<Map<String, Object>> articlesResponse = restTemplate.exchange(
                "/api/articles",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                });
        assertThat(articlesResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(articlesResponse.getBody()).isNotNull();
        assertThat(articlesResponse.getBody().get("code")).isEqualTo(200);

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.add("userId", "3");
        ResponseEntity<Map<String, Object>> assessmentResponse = restTemplate.exchange(
                "/api/assessments?scaleId=1&answers=%7B%221%22%3A1%2C%222%22%3A2%7D",
                HttpMethod.POST,
                new HttpEntity<>(userHeaders),
                new ParameterizedTypeReference<>() {
                });
        assertThat(assessmentResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(assessmentResponse.getBody()).isNotNull();
        assertThat(assessmentResponse.getBody().get("code")).isEqualTo(200);
        @SuppressWarnings("unchecked")
        Map<String, Object> assessment = (Map<String, Object>) assessmentResponse.getBody().get("data");
        assertThat(assessment.get("createdAt")).asString().isNotBlank();

        ResponseEntity<Map<String, Object>> appointmentResponse = restTemplate.exchange(
                "/api/appointments",
                HttpMethod.POST,
                new HttpEntity<>(Map.of(
                        "counselorId", 1,
                        "timeSlotId", 1,
                        "requirement", "想咨询近期焦虑问题",
                        "price", 300
                ), userHeaders),
                new ParameterizedTypeReference<>() {
                });
        assertThat(appointmentResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(appointmentResponse.getBody()).isNotNull();
        assertThat(appointmentResponse.getBody().get("code")).isEqualTo(200);
        @SuppressWarnings("unchecked")
        Map<String, Object> appointment = (Map<String, Object>) appointmentResponse.getBody().get("data");
        assertThat(appointment.get("createdAt")).asString().isNotBlank();
    }
}
