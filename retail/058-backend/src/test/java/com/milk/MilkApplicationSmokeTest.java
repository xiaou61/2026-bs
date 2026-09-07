package com.milk;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MilkApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void smokeTestCoreMilkOrderFlows() throws Exception {
        mockMvc.perform(get("/api/category/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        mockMvc.perform(get("/api/product/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        mockMvc.perform(get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(1));

        mockMvc.perform(get("/api/auth/info"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(401));

        String adminToken = login("admin");
        String userToken = login("user");
        String deliveryToken = login("delivery");

        mockMvc.perform(get("/api/stats/dashboard").header("Authorization", bearer(userToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403));

        getWithToken("/api/stats/dashboard", adminToken)
                .andExpect(jsonPath("$.data.userCount", notNullValue()));
        getWithToken("/api/user/page", adminToken);
        getWithToken("/api/category/page", adminToken);
        getWithToken("/api/product/page", adminToken);
        getWithToken("/api/area/list", adminToken);
        getWithToken("/api/route/list", adminToken);
        getWithToken("/api/order/page", adminToken);
        getWithToken("/api/subscription/page", adminToken);
        getWithToken("/api/complaint/page", adminToken);

        postJson("/api/address", userToken, Map.of(
                "contactName", "测试用户",
                "contactPhone", "13900000000",
                "province", "浙江省",
                "city", "杭州市",
                "district", "西湖区",
                "detail", "测试路58号",
                "isDefault", 0
        ));
        getWithToken("/api/address/list", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));

        postJson("/api/subscription", userToken, Map.of(
                "productId", 1,
                "addressId", 1,
                "type", "DAILY",
                "quantity", 1,
                "deliveryTime", "MORNING",
                "startDate", "2026-04-28",
                "endDate", "2026-05-28"
        ));
        getWithToken("/api/subscription/my", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/order/my", userToken);

        postJson("/api/complaint", userToken, Map.of(
                "orderId", 1,
                "type", "COMPLAINT",
                "content", "测试投诉内容"
        ));
        getWithToken("/api/complaint/my", userToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/notification/my", userToken);

        getWithToken("/api/delivery/today", deliveryToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        putJson("/api/delivery/complete/3", deliveryToken, Map.of());
        getWithToken("/api/delivery/history", deliveryToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));

        putJson("/api/complaint/reply", adminToken, Map.of(
                "id", 2,
                "reply", "已收到反馈，将优化配送时段"
        ));
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
