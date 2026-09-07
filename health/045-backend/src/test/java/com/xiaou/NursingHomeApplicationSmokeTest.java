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

import java.time.LocalDate;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NursingHomeApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldStartWithDemoDataAndSupportCoreRoleFlows() throws Exception {
        String adminToken = extractToken(login("admin", "admin123")
                .andExpect(jsonPath("$.data.role").value(2))
                .andExpect(jsonPath("$.data.realName").value("系统管理员"))
                .andReturn());

        mockMvc.perform(get("/api/notice/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(2)));

        mockMvc.perform(get("/api/admin/statistics")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.elderCount").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.data.bedStats.total").value(greaterThanOrEqualTo(4)));

        mockMvc.perform(get("/api/elder/list")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(2)));

        String nurseToken = extractToken(login("nurse1", "admin123")
                .andExpect(jsonPath("$.data.role").value(1))
                .andReturn());

        mockMvc.perform(post("/api/health")
                        .header("Authorization", "Bearer " + nurseToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "elderId", 1,
                                "bloodPressure", "126/80",
                                "heartRate", 78,
                                "temperature", 36.6,
                                "remark", "自动化巡检记录"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/health/1")
                        .header("Authorization", "Bearer " + nurseToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(2)));

        String familyToken = extractToken(login("family1", "admin123")
                .andExpect(jsonPath("$.data.role").value(0))
                .andExpect(jsonPath("$.data.elderId").value(1))
                .andReturn());

        mockMvc.perform(get("/api/bill/list")
                        .header("Authorization", "Bearer " + familyToken)
                        .param("elderId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(1)));

        mockMvc.perform(post("/api/bill/pay")
                        .header("Authorization", "Bearer " + familyToken)
                        .param("billId", "1")
                        .param("amount", "1000")
                        .param("payMethod", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/bill/1")
                        .header("Authorization", "Bearer " + familyToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.status").value(1))
                .andExpect(jsonPath("$.data.paidAmount").value(1000.0));

        mockMvc.perform(post("/api/visit/apply")
                        .header("Authorization", "Bearer " + familyToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "elderId", 1,
                                "visitorName", "张明",
                                "visitorPhone", "13900000011",
                                "relationship", "子女",
                                "visitDate", LocalDate.now().plusDays(1).toString(),
                                "visitTime", "09:00-10:00",
                                "visitorCount", 2,
                                "purpose", "周末探望"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        Long visitId = extractFirstVisitId(familyToken);
        mockMvc.perform(post("/api/visit/approve/" + visitId)
                        .header("Authorization", "Bearer " + adminToken)
                        .param("status", "1")
                        .param("remark", "同意探访"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
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

    private Long extractFirstVisitId(String token) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/visit/my")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(1)))
                .andReturn();
        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());
        return root.path("data").path("records").get(0).path("id").asLong();
    }

    private String extractToken(MvcResult result) throws Exception {
        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());
        return root.path("data").path("token").asText();
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
