package com.mfg;

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
class MfgApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void smokeTestCoreMfgErpFlows() throws Exception {
        mockMvc.perform(get("/api/user/info"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(401));

        String adminToken = login("admin");
        String managerToken = login("manager");
        String operatorToken = login("operator");
        String inspectorToken = login("inspector");

        mockMvc.perform(get("/api/user/page").header("Authorization", bearer(operatorToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403));

        getWithToken("/api/dashboard/overview", adminToken)
                .andExpect(jsonPath("$.data.equipmentTotal", greaterThanOrEqualTo(1)));
        getWithToken("/api/dashboard/equipmentStatus", adminToken);
        getWithToken("/api/dashboard/orderStats", adminToken);
        getWithToken("/api/dashboard/alertStats", adminToken);
        getWithToken("/api/dashboard/qualityTrend", adminToken);

        getWithToken("/api/user/page", adminToken)
                .andExpect(jsonPath("$.data.records.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/equipmentCategory/page", adminToken);
        getWithToken("/api/equipmentCategory/list", adminToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/equipment/page", adminToken);
        getWithToken("/api/sensorData/page", adminToken);
        getWithToken("/api/sensorData/latest/1", adminToken)
                .andExpect(jsonPath("$.data.equipmentId").value(1));
        getWithToken("/api/sensorData/trend/1?limit=3", adminToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/alert/page", adminToken);
        getWithToken("/api/alert/stats", adminToken);
        getWithToken("/api/product/page", adminToken);
        getWithToken("/api/product/list", adminToken)
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
        getWithToken("/api/productionOrder/page", adminToken);
        getWithToken("/api/material/page", adminToken);
        getWithToken("/api/materialStock/page", adminToken);
        getWithToken("/api/qualityInspection/page", adminToken);
        getWithToken("/api/maintenancePlan/page", adminToken);
        getWithToken("/api/maintenanceRecord/page", adminToken);

        putJson("/api/equipment/status", operatorToken, Map.of(
                "id", 3,
                "status", "running"
        ));
        postJson("/api/sensorData", operatorToken, Map.of(
                "equipmentId", 1,
                "temperature", 48.5,
                "pressure", 7.2,
                "vibration", 0.16,
                "speed", 3490
        ));
        putJson("/api/alert/handle/2", operatorToken, Map.of());

        postJson("/api/materialStock/in", managerToken, Map.of(
                "materialId", 1,
                "quantity", 10,
                "reason", "测试入库",
                "operator", "张经理"
        ));
        postJson("/api/materialStock/out", managerToken, Map.of(
                "materialId", 1,
                "quantity", 5,
                "reason", "测试出库",
                "operator", "张经理"
        ));
        putJson("/api/productionOrder/status", managerToken, Map.of(
                "id", 3,
                "status", "producing"
        ));

        getWithToken("/api/qualityInspection/page", inspectorToken);
        postJson("/api/qualityInspection", inspectorToken, Map.of(
                "orderId", 1,
                "productId", 1,
                "inspectQuantity", 20,
                "qualifiedQuantity", 19,
                "unqualifiedQuantity", 1,
                "result", "passed",
                "inspector", "王质检员",
                "remark", "测试质检"
        ));

        putJson("/api/maintenanceRecord/status", operatorToken, Map.of(
                "id", 2,
                "status", "completed"
        ));

        postJson("/api/logout", adminToken, Map.of());
    }

    private String login(String username) throws Exception {
        String response = mockMvc.perform(post("/api/login")
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
