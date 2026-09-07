package com.xiaou.health;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.xiaou.health.dto.ConsultationCreateRequest;
import com.xiaou.health.dto.HealthDataCreateRequest;
import com.xiaou.health.dto.LoginRequest;
import com.xiaou.health.dto.RegisterRequest;
import com.xiaou.health.entity.HealthKnowledge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HealthManagementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterAndLoginUnderDefaultConfiguration() throws Exception {
        String username = "patient_" + System.currentTimeMillis();

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setPassword("123456");
        registerRequest.setRole("PATIENT");
        registerRequest.setEmail(username + "@example.com");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.password").doesNotExist());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword("123456");

        MvcResult loginResult = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token").isString())
                .andExpect(jsonPath("$.data.username").value(username))
                .andExpect(jsonPath("$.data.role").value("PATIENT"))
                .andReturn();

        JsonNode loginJson = objectMapper.readTree(loginResult.getResponse().getContentAsString());
        String token = loginJson.path("data").path("token").asText();

        HealthDataCreateRequest createRequest = new HealthDataCreateRequest();
        createRequest.setDataType("WEIGHT");
        createRequest.setValue(new BigDecimal("65.50"));
        createRequest.setUnit("kg");
        createRequest.setRemark("测试记录");

        mockMvc.perform(post("/api/health-data")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.dataType").value("WEIGHT"))
                .andExpect(jsonPath("$.data.value").value(65.50));

        mockMvc.perform(get("/api/health-data/list")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].dataType").value("WEIGHT"));

        mockMvc.perform(get("/api/doctors/pending")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(403));
    }

    @Test
    void shouldSupportKnowledgeCenterWithPublicReadAndAdminManage() throws Exception {
        String adminToken = loginAndGetToken("admin", "admin123");
        String patientToken = loginAndGetToken("patient", "patient123");
        String title = "健康知识测试-" + System.currentTimeMillis();

        HealthKnowledge knowledge = new HealthKnowledge();
        knowledge.setCategory("慢病管理");
        knowledge.setTitle(title);
        knowledge.setSummary("用于验证公开列表、详情和管理员管理能力。");
        knowledge.setContent("这是自动化测试生成的健康知识内容。");
        knowledge.setStatus(1);

        MvcResult createResult = mockMvc.perform(post("/api/health-knowledge")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(knowledge)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.title").value(title))
                .andReturn();

        JsonNode createJson = objectMapper.readTree(createResult.getResponse().getContentAsString());
        long knowledgeId = createJson.path("data").path("id").asLong();

        mockMvc.perform(get("/api/health-knowledge/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[*].title", hasItem(title)));

        mockMvc.perform(get("/api/health-knowledge/detail/" + knowledgeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.title").value(title));

        mockMvc.perform(post("/api/health-knowledge")
                        .header("Authorization", "Bearer " + patientToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(knowledge)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(403));

        mockMvc.perform(put("/api/health-knowledge/" + knowledgeId + "/status")
                        .header("Authorization", "Bearer " + adminToken)
                        .param("status", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.status").value(0));

        mockMvc.perform(delete("/api/health-knowledge/" + knowledgeId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void shouldGenerateAssessmentAndAlertsAfterAbnormalHealthData() throws Exception {
        String username = "risk_patient_" + System.currentTimeMillis();

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setPassword("123456");
        registerRequest.setRole("PATIENT");
        registerRequest.setEmail(username + "@example.com");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        String patientToken = loginAndGetToken(username, "123456");
        String adminToken = loginAndGetToken("admin", "admin123");

        HealthDataCreateRequest request = new HealthDataCreateRequest();
        request.setDataType("血糖");
        request.setValue(new BigDecimal("12.6"));
        request.setUnit("mmol/L");
        request.setRemark("餐后偏高");

        mockMvc.perform(post("/api/health-data")
                        .header("Authorization", "Bearer " + patientToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/health-assessment/latest")
                        .header("Authorization", "Bearer " + patientToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.bloodSugarLevel").value("HIGH"))
                .andExpect(jsonPath("$.data.healthScore").isNumber());

        MvcResult alertsResult = mockMvc.perform(get("/api/health-assessment/alerts")
                        .header("Authorization", "Bearer " + patientToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].alertType").value("BLOOD_SUGAR"))
                .andReturn();

        JsonNode alertsJson = objectMapper.readTree(alertsResult.getResponse().getContentAsString());
        long alertId = alertsJson.path("data").path(0).path("id").asLong();

        mockMvc.perform(get("/api/health-assessment/alerts/unread-count")
                        .header("Authorization", "Bearer " + patientToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value(1));

        mockMvc.perform(put("/api/health-assessment/alerts/" + alertId + "/read")
                        .header("Authorization", "Bearer " + patientToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.isRead").value(1));

        mockMvc.perform(get("/api/health-assessment/latest")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(403));
    }

    @Test
    void shouldProvideAdminAndDoctorStatisticsDashboardData() throws Exception {
        JsonNode adminLogin = loginAndGetData("admin", "admin123");
        JsonNode doctorLogin = loginAndGetData("doctor", "doctor123");
        JsonNode patientLogin = loginAndGetData("patient", "patient123");

        String adminToken = adminLogin.path("token").asText();
        String doctorToken = doctorLogin.path("token").asText();
        String patientToken = patientLogin.path("token").asText();
        long doctorUserId = doctorLogin.path("userId").asLong();

        ConsultationCreateRequest request = new ConsultationCreateRequest();
        request.setDoctorId(doctorUserId);
        request.setTitle("统计模块测试咨询");
        request.setQuestion("用于验证医生和管理员统计面板是否返回真实数据。");

        mockMvc.perform(post("/api/consultations")
                        .header("Authorization", "Bearer " + patientToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/statistics/admin")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.totalUsers", greaterThanOrEqualTo(3)))
                .andExpect(jsonPath("$.data.totalDoctors", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.data.totalPatients", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.data.consultationTrend", hasSize(7)))
                .andExpect(jsonPath("$.data.healthDataTrend", hasSize(7)))
                .andExpect(jsonPath("$.data.doctorServiceRanking").isArray());

        mockMvc.perform(get("/api/statistics/doctor")
                        .header("Authorization", "Bearer " + doctorToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.totalConsultations", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.data.pendingConsultations", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.data.consultationTrend", hasSize(7)))
                .andExpect(jsonPath("$.data.recentPendingConsultations").isArray());

        mockMvc.perform(get("/api/statistics/admin")
                        .header("Authorization", "Bearer " + patientToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(403));

        mockMvc.perform(get("/api/statistics/doctor")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(403));
    }

    private String loginAndGetToken(String username, String password) throws Exception {
        return loginAndGetData(username, password).path("token").asText();
    }

    private JsonNode loginAndGetData(String username, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        MvcResult loginResult = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();

        JsonNode loginJson = objectMapper.readTree(loginResult.getResponse().getContentAsString());
        return loginJson.path("data");
    }
}
