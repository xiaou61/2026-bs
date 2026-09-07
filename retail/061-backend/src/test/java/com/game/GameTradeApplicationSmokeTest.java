package com.game;

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
class GameTradeApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void smokeTestGameTradeCoreFlows() throws Exception {
        mockMvc.perform(get("/api/auth/info"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(401));

        String adminToken = login("admin");
        String sellerToken = login("user2");
        String buyerToken = login("user3");

        mockMvc.perform(get("/api/user/page").header("Authorization", bearer(buyerToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403));

        getWithToken("/api/dashboard/stats", adminToken)
                .andExpect(jsonPath("$.data.userCount", greaterThanOrEqualTo(4)))
                .andExpect(jsonPath("$.data.itemCount", greaterThanOrEqualTo(8)));
        getWithToken("/api/dashboard/categoryStats", adminToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/dashboard/orderTrend", adminToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(7)));
        getWithToken("/api/dashboard/hotItems", adminToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));

        getWithToken("/api/category/list", buyerToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/category/page", adminToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/item/list", buyerToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/item/1", buyerToken)
                .andExpect(jsonPath("$.data.title").value("王者荣耀 国服百星号"));
        getWithToken("/api/item/page", sellerToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));

        getWithToken("/api/favorite/check/2", buyerToken)
                .andExpect(jsonPath("$.data").value(true));
        postJson("/api/favorite/3", buyerToken, Map.of());
        getWithToken("/api/favorite/check/3", buyerToken)
                .andExpect(jsonPath("$.data").value(true));
        getWithToken("/api/favorite/my", buyerToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));

        JsonNode order = postJson("/api/order", buyerToken, Map.of(
                "itemId", 6,
                "quantity", 1,
                "remark", "自动化测试下单"
        ));
        long orderId = order.path("data").path("id").asLong();
        putJson("/api/order/pay/" + orderId, buyerToken, Map.of());
        mockMvc.perform(put("/api/order/complete/2").header("Authorization", bearer(sellerToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403));
        putJson("/api/order/deliver/" + orderId, sellerToken, Map.of());
        putJson("/api/order/complete/" + orderId, buyerToken, Map.of());
        getWithToken("/api/order/my", buyerToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/order/sale", sellerToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/order/page", adminToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));

        postJson("/api/review", buyerToken, Map.of(
                "orderId", orderId,
                "rating", 5,
                "content", "交易顺利，服务很好"
        ));
        getWithToken("/api/review/item/6", buyerToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/review/page", adminToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));

        JsonNode complaint = postJson("/api/complaint", buyerToken, Map.of(
                "orderId", orderId,
                "type", "ORDER",
                "content", "自动化测试申诉"
        ));
        getWithToken("/api/complaint/my", buyerToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/complaint/page", adminToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));
        putJson("/api/complaint/reply", adminToken, Map.of(
                "id", complaint.path("data").path("id").asLong(2),
                "reply", "测试处理完成"
        ));

        getWithToken("/api/announcement/list", buyerToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/announcement/page", adminToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));

        mockMvc.perform(delete("/api/review/2").header("Authorization", bearer(adminToken)))
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

    private JsonNode postJson(String path, String token, Map<String, ?> body) throws Exception {
        String response = mockMvc.perform(post(path)
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readTree(response);
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
