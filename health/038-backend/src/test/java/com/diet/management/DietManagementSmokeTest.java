package com.diet.management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DietManagementSmokeTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void defaultDemoDataSupportsLoginFoodAndNutritionApis() {
        Map<?, ?> login = post("/api/user/login?username=admin&password=123456");
        assertThat(login.get("code")).isEqualTo(200);
        assertThat(login.get("data")).isInstanceOf(String.class);

        Map<?, ?> foodPage = get("/api/food/page?current=1&size=5");
        assertThat(foodPage.get("code")).isEqualTo(200);
        Map<?, ?> foodData = (Map<?, ?>) foodPage.get("data");
        assertThat(foodData.get("total")).isEqualTo(10);

        Map<?, ?> nutrition = get("/api/nutrition/daily?userId=2");
        assertThat(nutrition.get("code")).isEqualTo(200);
        Map<?, ?> nutritionData = (Map<?, ?>) nutrition.get("data");
        assertThat(nutritionData.get("calorie")).isNotNull();
        assertThat(nutritionData.get("goal")).isNotNull();
    }

    private Map<?, ?> get(String path) {
        return restTemplate.getForObject(url(path), Map.class);
    }

    private Map<?, ?> post(String path) {
        return restTemplate.postForObject(url(path), null, Map.class);
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }
}
