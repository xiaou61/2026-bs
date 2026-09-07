package com.xiaou;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ScriptKillApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldStartWithDemoDataAndSupportCoreRoleFlows() throws Exception {
        String adminToken = extractToken(login("admin", "123456")
                .andExpect(jsonPath("$.data.role").value(3))
                .andReturn());

        mockMvc.perform(get("/api/notice/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(2)));

        mockMvc.perform(get("/api/admin/statistics")
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.users").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.data.shops").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.data.scripts").value(greaterThanOrEqualTo(5)));

        String userToken = extractToken(login("user1", "123456")
                .andExpect(jsonPath("$.data.role").value(0))
                .andReturn());

        mockMvc.perform(get("/api/script/categories")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(5)));

        mockMvc.perform(get("/api/script/list")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(5)));

        mockMvc.perform(get("/api/shop/list")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(2)));

        mockMvc.perform(get("/api/shop/1/rooms")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(3)));

        mockMvc.perform(post("/api/reservation")
                        .header("Authorization", bearer(userToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "shopId", 1,
                                "roomId", 1,
                                "scriptId", 1,
                                "reservationDate", LocalDate.now().plusDays(1).toString(),
                                "timeSlot", "14:00-18:00",
                                "playerCount", 6,
                                "contactName", "测试玩家",
                                "contactPhone", "13800000005",
                                "totalPrice", new BigDecimal("268.00"),
                                "remark", "自动化巡检预约"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        Long reservationId = extractReservationIdByContact(userToken, "测试玩家");

        String ownerToken = extractToken(login("shop1", "123456")
                .andExpect(jsonPath("$.data.role").value(1))
                .andReturn());

        mockMvc.perform(get("/api/owner/shop")
                        .header("Authorization", bearer(ownerToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(1));

        mockMvc.perform(put("/api/owner/reservation/" + reservationId + "/confirm")
                        .header("Authorization", bearer(ownerToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(put("/api/owner/reservation/" + reservationId + "/complete")
                        .header("Authorization", bearer(ownerToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/reservation/" + reservationId)
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.status").value(3));

        mockMvc.perform(post("/api/user/favorite")
                        .header("Authorization", bearer(userToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "type", 1,
                                "targetId", 5
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(post("/api/user/review")
                        .header("Authorization", bearer(userToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "reservationId", reservationId,
                                "shopId", 1,
                                "scriptId", 1,
                                "rating", 5,
                                "content", "自动化巡检评价"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        String authorToken = extractToken(login("author1", "123456")
                .andExpect(jsonPath("$.data.role").value(2))
                .andReturn());

        mockMvc.perform(post("/api/author/script")
                        .header("Authorization", bearer(authorToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "categoryId", 2,
                                "title", "自动化巡检剧本",
                                "description", "用于验证作者创作与管理员审核链路",
                                "difficulty", 2,
                                "playerCount", "6人",
                                "duration", "4小时",
                                "type", 2,
                                "price", new BigDecimal("1688.00"),
                                "contents", List.of(Map.of(
                                        "chapterName", "序章",
                                        "roleName", "侦探",
                                        "content", "案发现场一片寂静。",
                                        "sort", 1
                                ))
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        Long scriptId = extractScriptIdByTitle(authorToken, "自动化巡检剧本");
        mockMvc.perform(put("/api/admin/script/" + scriptId + "/audit")
                        .header("Authorization", bearer(adminToken))
                        .param("status", "1")
                        .param("remark", "审核通过"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/script/list")
                        .header("Authorization", bearer(userToken))
                        .param("keyword", "自动化巡检剧本"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(1)));
    }

    private ResultActionsWrapper login(String username, String password) throws Exception {
        return new ResultActionsWrapper(mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "username", username,
                        "password", password
                ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token").isNotEmpty()));
    }

    private Long extractReservationIdByContact(String token, String contactName) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/reservation/my")
                        .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();
        JsonNode records = readJson(result).path("data").path("records");
        for (JsonNode record : records) {
            if (contactName.equals(record.path("contactName").asText())) {
                return record.path("id").asLong();
            }
        }
        throw new AssertionError("未找到预约记录：" + contactName);
    }

    private Long extractScriptIdByTitle(String token, String title) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/author/scripts")
                        .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();
        JsonNode records = readJson(result).path("data").path("records");
        for (JsonNode record : records) {
            if (title.equals(record.path("title").asText())) {
                return record.path("id").asLong();
            }
        }
        throw new AssertionError("未找到作者剧本：" + title);
    }

    private String extractToken(MvcResult result) throws Exception {
        JsonNode root = readJson(result);
        return root.path("data").path("token").asText();
    }

    private JsonNode readJson(MvcResult result) throws Exception {
        return objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }

    private record ResultActionsWrapper(org.springframework.test.web.servlet.ResultActions delegate) {
        ResultActionsWrapper andExpect(org.springframework.test.web.servlet.ResultMatcher matcher) throws Exception {
            delegate.andExpect(matcher);
            return this;
        }

        MvcResult andReturn() {
            return delegate.andReturn();
        }
    }
}
