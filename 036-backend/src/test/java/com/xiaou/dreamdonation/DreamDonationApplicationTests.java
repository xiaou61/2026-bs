package com.xiaou.dreamdonation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DreamDonationApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loginShouldReturnTokenAndHidePassword() throws Exception {
        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("username", "admin", "password", "123456"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token", not(blankOrNullString())))
                .andExpect(jsonPath("$.data.user.username").value("admin"))
                .andExpect(jsonPath("$.data.user.password").doesNotExist());
    }

    @Test
    void userInfoShouldUseJwtUserInsteadOfDefaultAdmin() throws Exception {
        String userToken = login("user1");

        mockMvc.perform(get("/api/user/info").header("Authorization", userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.username").value("user1"))
                .andExpect(jsonPath("$.data.realName").value("张三"))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    @Test
    void projectCreationShouldRequireOrganizationOrAdmin() throws Exception {
        String userToken = login("user1");
        String orgToken = login("org1");

        mockMvc.perform(post("/api/projects")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(projectPayload("普通用户越权项目"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(403));

        mockMvc.perform(post("/api/projects")
                        .header("Authorization", orgToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(projectPayload("组织发起助学项目"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.title").value("组织发起助学项目"))
                .andExpect(jsonPath("$.data.creator.password").doesNotExist());
    }

    @Test
    void donationShouldRequireAuthenticationAndValidateAmount() throws Exception {
        String userToken = login("user1");

        mockMvc.perform(post("/api/donations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(donationPayload(BigDecimal.TEN))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(401));

        mockMvc.perform(post("/api/donations")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(donationPayload(BigDecimal.ZERO))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400));

        mockMvc.perform(post("/api/donations")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(donationPayload(new BigDecimal("88.88")))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.amount").value(88.88))
                .andExpect(jsonPath("$.data.user").doesNotExist())
                .andExpect(jsonPath("$.data.project.creator.password").doesNotExist());
    }

    private String login(String username) throws Exception {
        String response = mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("username", username, "password", "123456"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn()
                .getResponse()
                .getContentAsString();
        JsonNode root = objectMapper.readTree(response);
        return root.path("data").path("token").asText();
    }

    private Map<String, Object> projectPayload(String title) {
        return Map.of(
                "title", title,
                "description", "用于巡检验证的公益项目",
                "category", "教育助学",
                "targetAmount", 1000,
                "startDate", "2030-01-01 00:00:00",
                "endDate", "2030-02-01 00:00:00",
                "organizationName", "巡检公益组织",
                "location", "浙江省"
        );
    }

    private Map<String, Object> donationPayload(BigDecimal amount) {
        return Map.of(
                "projectId", 1,
                "amount", amount,
                "message", "巡检捐赠",
                "anonymous", true,
                "paymentMethod", "ALIPAY"
        );
    }

    private String json(Object value) throws Exception {
        return objectMapper.writeValueAsString(value);
    }
}
