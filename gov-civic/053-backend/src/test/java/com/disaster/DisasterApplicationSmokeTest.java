package com.disaster;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "debug=false")
@AutoConfigureMockMvc
class DisasterApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldStartWithDemoDataAndSupportCoreRescueFlows() throws Exception {
        String adminToken = extractToken(login("admin", "123456")
                .andExpect(jsonPath("$.data.user.role").value("ADMIN"))
                .andExpect(jsonPath("$.data.user.password").doesNotExist())
                .andReturn());
        String reporterToken = extractToken(login("reporter", "123456")
                .andExpect(jsonPath("$.data.user.role").value("REPORTER"))
                .andExpect(jsonPath("$.data.user.password").doesNotExist())
                .andReturn());

        mockMvc.perform(get("/api/stats/overview").header("Authorization", bearer(reporterToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403));

        mockMvc.perform(get("/api/stats/overview").header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.userCount").value(greaterThanOrEqualTo(4)))
                .andExpect(jsonPath("$.data.disasterCount").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.data.warehouseCount").value(greaterThanOrEqualTo(3)))
                .andExpect(jsonPath("$.data.materialCount").value(greaterThanOrEqualTo(12)));

        mockMvc.perform(get("/api/user/info").header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.password").doesNotExist());

        mockMvc.perform(get("/api/category/list").header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(5)));

        mockMvc.perform(get("/api/material/list").header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(10)));

        mockMvc.perform(get("/api/warehouse/list").header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(3)));

        mockMvc.perform(post("/api/disaster/report")
                        .header("Authorization", bearer(reporterToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of(
                                "title", "自动化暴雨灾情",
                                "type", "FLOOD",
                                "level", 4,
                                "province", "浙江省",
                                "city", "杭州市",
                                "address", "自动化测试街道",
                                "affectedCount", 1200,
                                "description", "自动化巡检灾情上报"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(post("/api/task/create")
                        .header("Authorization", bearer(adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of(
                                "disasterId", 1,
                                "title", "自动化救援任务",
                                "content", "转移群众并发放物资",
                                "priority", 4
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andDo(result -> mockMvc.perform(get("/api/task/list").header("Authorization", bearer(adminToken)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.code").value(200))
                        .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(1))))
                .andReturn();
        Long taskId = firstRecordId("/api/task/list", adminToken);

        mockMvc.perform(put("/api/task/{id}/status", taskId)
                        .header("Authorization", bearer(adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("status", 1))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(post("/api/stock/in")
                        .header("Authorization", bearer(adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of(
                                "warehouseId", 1,
                                "materialId", 1,
                                "quantity", 50,
                                "source", "自动化入库",
                                "remark", "补充库存"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(post("/api/dispatch/create")
                        .header("Authorization", bearer(adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of(
                                "disasterId", 1,
                                "fromWarehouseId", 1,
                                "toAddress", "自动化测试街道",
                                "priority", 2,
                                "remark", "自动化调度",
                                "items", List.of(Map.of("materialId", 1, "quantity", 10))
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
        Long dispatchId = firstRecordId("/api/dispatch/list", adminToken);

        mockMvc.perform(put("/api/dispatch/{id}/approve", dispatchId)
                        .header("Authorization", bearer(adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("approved", true, "remark", "同意调度"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/notice/published").header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(2)));
    }

    private ResultActions login(String username, String password) throws Exception {
        return mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("username", username, "password", password))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token").isNotEmpty());
    }

    private Long firstRecordId(String path, String token) throws Exception {
        MvcResult result = mockMvc.perform(get(path).header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();
        return readJson(result).path("data").path("records").get(0).path("id").asLong();
    }

    private String extractToken(MvcResult result) throws Exception {
        return readJson(result).path("data").path("token").asText();
    }

    private JsonNode readJson(MvcResult result) throws Exception {
        return objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    private String json(Object value) throws Exception {
        return objectMapper.writeValueAsString(value);
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}
