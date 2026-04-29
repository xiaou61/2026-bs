package com.movie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovieApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void smokeTestMovieTicketCoreFlows() throws Exception {
        mockMvc.perform(get("/api/auth/info"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(401));

        String adminToken = login("admin");
        String userToken = login("user1");

        mockMvc.perform(get("/api/user/page").header("Authorization", bearer(userToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403));
        mockMvc.perform(get("/api/dashboard/stats").header("Authorization", bearer(userToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403));

        getWithToken("/api/dashboard/stats", adminToken)
                .andExpect(jsonPath("$.data.userCount", greaterThanOrEqualTo(3)))
                .andExpect(jsonPath("$.data.movieCount", greaterThanOrEqualTo(6)));
        getWithToken("/api/dashboard/categoryStats", adminToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/dashboard/orderTrend", adminToken);
        getWithToken("/api/dashboard/boxOfficeRank", adminToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));

        getWithToken("/api/user/page", adminToken)
                .andExpect(jsonPath("$.data.list.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/movieCategory/page", adminToken);
        getWithToken("/api/movieCategory/list", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/movie/page", userToken)
                .andExpect(jsonPath("$.data.list.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/movie/1", userToken)
                .andExpect(jsonPath("$.data.title").value("星际迷航：新纪元"));
        getWithToken("/api/movie/hot", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/cinema/page", userToken);
        getWithToken("/api/cinema/list", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/hall/page", adminToken);
        getWithToken("/api/hall/list?cinemaId=1", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/showtime/page", adminToken);
        getWithToken("/api/showtime/movie/1", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/announcement/page", userToken);
        getWithToken("/api/announcement/list", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));

        getWithToken("/api/favorite/check/2", userToken)
                .andExpect(jsonPath("$.data").value(false));
        postJson("/api/favorite/2", userToken, Map.of());
        getWithToken("/api/favorite/check/2", userToken)
                .andExpect(jsonPath("$.data").value(true));
        getWithToken("/api/favorite/my", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));

        postJson("/api/order", userToken, Map.of(
                "showtimeId", 2,
                "seats", "2排1座,2排2座",
                "seatCount", 2
        ));
        getWithToken("/api/order/my", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        putJson("/api/order/pay/6", userToken, Map.of());
        mockMvc.perform(put("/api/order/pay/3").header("Authorization", bearer(userToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403));
        getWithToken("/api/order/page", adminToken)
                .andExpect(jsonPath("$.data.list.length()", greaterThanOrEqualTo(1)));
        putJson("/api/order/complete/1", adminToken, Map.of());

        getWithToken("/api/review/movie/1", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        postJson("/api/review", userToken, Map.of(
                "movieId", 1,
                "rating", 9,
                "content", "自动化测试影评"
        ));
        getWithToken("/api/review/page", adminToken)
                .andExpect(jsonPath("$.data.list.length()", greaterThanOrEqualTo(1)));
        mockMvc.perform(delete("/api/review/7").header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    private String login(String username) throws Exception {
        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", username,
                                "password", "123456"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token", notNullValue()))
                .andExpect(jsonPath("$.data.user.password").doesNotExist())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JsonNode root = objectMapper.readTree(response);
        return root.path("data").path("token").asText();
    }

    private org.springframework.test.web.servlet.ResultActions getWithToken(String path, String token) throws Exception {
        return mockMvc.perform(get(path).header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    private void postJson(String path, String token, Map<String, ?> body) throws Exception {
        mockMvc.perform(post(path)
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    private void putJson(String path, String token, Map<String, ?> body) throws Exception {
        mockMvc.perform(put(path)
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}
