package com.xiaou.rice;

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
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RiceHarvestApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCompleteCoreBookingFlowAndEnforceRoles() throws Exception {
        JsonNode health = readJson(mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(health.path("code").asInt()).isEqualTo(200);
        assertThat(health.path("data").asText()).isEqualTo("ok");

        String adminToken = login("admin", "123456");

        JsonNode invalidRegister = readJson(mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", "bad_role_user",
                                "password", "123456",
                                "nickname", "非法角色",
                                "role", 9
                        ))))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(invalidRegister.path("code").asInt()).isEqualTo(400);

        String farmerToken = register("farmer_check_035", "巡检农户", 1);
        String driverToken = register("driver_check_035", "巡检机手", 2);
        String otherDriverToken = register("driver_check_035_b", "巡检机手B", 2);

        JsonNode me = readJson(mockMvc.perform(
                get("/api/auth/me")
                        .header("Authorization", bearer(farmerToken)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(me.path("code").asInt()).isEqualTo(200);
        assertThat(me.path("data").path("username").asText()).isEqualTo("farmer_check_035");
        assertThat(me.path("data").has("password")).isFalse();

        JsonNode driverCreatePlot = readJson(mockMvc.perform(
                post("/api/plots")
                        .header("Authorization", bearer(driverToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plotPayload("机手非法地块", new BigDecimal("10.50")))))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(driverCreatePlot.path("code").asInt()).isEqualTo(403);

        JsonNode farmerCreateMachine = readJson(mockMvc.perform(
                post("/api/machines")
                        .header("Authorization", bearer(farmerToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machinePayload("农户非法设备"))))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(farmerCreateMachine.path("code").asInt()).isEqualTo(403);

        JsonNode plotCreate = readJson(mockMvc.perform(
                post("/api/plots")
                        .header("Authorization", bearer(farmerToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plotPayload("巡检地块035", new BigDecimal("18.80")))))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(plotCreate.path("code").asInt()).isEqualTo(200);
        long plotId = plotCreate.path("data").path("id").asLong();
        assertThat(plotId).isPositive();

        JsonNode machineCreate = readJson(mockMvc.perform(
                post("/api/machines")
                        .header("Authorization", bearer(driverToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machinePayload("巡检收割机035"))))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(machineCreate.path("code").asInt()).isEqualTo(200);
        long machineId = machineCreate.path("data").path("id").asLong();
        assertThat(machineId).isPositive();

        Map<String, Object> bookingPayload = new LinkedHashMap<>();
        bookingPayload.put("plotId", plotId);
        bookingPayload.put("expectDate", "2030-10-01");
        bookingPayload.put("timeWindow", "上午");
        bookingPayload.put("area", new BigDecimal("18.80"));
        bookingPayload.put("address", "湖南省测试村 35 号地");
        bookingPayload.put("latitude", 28.19409D);
        bookingPayload.put("longitude", 112.98228D);
        bookingPayload.put("remark", "035 巡检预约");

        JsonNode bookingCreate = readJson(mockMvc.perform(
                post("/api/bookings")
                        .header("Authorization", bearer(farmerToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingPayload)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(bookingCreate.path("code").asInt()).isEqualTo(200);
        long bookingId = bookingCreate.path("data").path("id").asLong();
        assertThat(bookingId).isPositive();

        JsonNode farmerAssign = readJson(mockMvc.perform(
                patch("/api/bookings/{id}/assign", bookingId)
                        .header("Authorization", bearer(farmerToken))
                        .param("machineId", String.valueOf(machineId)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(farmerAssign.path("code").asInt()).isEqualTo(403);

        JsonNode adminAssign = readJson(mockMvc.perform(
                patch("/api/bookings/{id}/assign", bookingId)
                        .header("Authorization", bearer(adminToken))
                        .param("machineId", String.valueOf(machineId)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(adminAssign.path("code").asInt()).isEqualTo(200);
        assertThat(adminAssign.path("data").asBoolean()).isTrue();

        JsonNode otherDriverStart = readJson(mockMvc.perform(
                patch("/api/bookings/{id}/start", bookingId)
                        .header("Authorization", bearer(otherDriverToken)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(otherDriverStart.path("code").asInt()).isEqualTo(403);

        JsonNode farmerStart = readJson(mockMvc.perform(
                patch("/api/bookings/{id}/start", bookingId)
                        .header("Authorization", bearer(farmerToken)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(farmerStart.path("code").asInt()).isEqualTo(403);

        JsonNode driverStart = readJson(mockMvc.perform(
                patch("/api/bookings/{id}/start", bookingId)
                        .header("Authorization", bearer(driverToken)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(driverStart.path("code").asInt()).isEqualTo(200);
        assertThat(driverStart.path("data").asBoolean()).isTrue();

        JsonNode otherDriverFinish = readJson(mockMvc.perform(
                patch("/api/bookings/{id}/finish", bookingId)
                        .header("Authorization", bearer(otherDriverToken))
                        .param("settleAmount", "1888.00"))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(otherDriverFinish.path("code").asInt()).isEqualTo(403);

        JsonNode driverFinish = readJson(mockMvc.perform(
                patch("/api/bookings/{id}/finish", bookingId)
                        .header("Authorization", bearer(driverToken))
                        .param("settleAmount", "1888.00"))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(driverFinish.path("code").asInt()).isEqualTo(200);
        assertThat(driverFinish.path("data").asBoolean()).isTrue();

        JsonNode bookingList = readJson(mockMvc.perform(
                get("/api/bookings")
                        .header("Authorization", bearer(farmerToken)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(bookingList.path("code").asInt()).isEqualTo(200);
        JsonNode booking = bookingList.path("data").get(0);
        assertThat(booking.path("id").asLong()).isEqualTo(bookingId);
        assertThat(booking.path("status").asText()).isEqualTo("SETTLED");
        assertThat(booking.path("settleAmount").decimalValue()).isEqualByComparingTo("1888.00");
        assertThat(booking.path("machineId").asLong()).isEqualTo(machineId);
    }

    private String login(String username, String password) throws Exception {
        JsonNode loginResponse = readJson(mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", username,
                                "password", password
                        ))))
                .andExpect(status().isOk())
                .andReturn());

        assertThat(loginResponse.path("code").asInt()).isEqualTo(200);
        String token = loginResponse.path("data").path("token").asText();
        assertThat(token).isNotBlank();
        return token;
    }

    private String register(String username, String nickname, int role) throws Exception {
        JsonNode registerResponse = readJson(mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", username,
                                "password", "123456",
                                "phone", "1390000" + role + "035",
                                "nickname", nickname,
                                "role", role
                        ))))
                .andExpect(status().isOk())
                .andReturn());

        assertThat(registerResponse.path("code").asInt()).isEqualTo(200);
        String token = registerResponse.path("data").path("token").asText();
        assertThat(token).isNotBlank();
        return token;
    }

    private Map<String, Object> plotPayload(String name, BigDecimal area) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("name", name);
        payload.put("area", area);
        payload.put("cropVariety", "湘晚籼");
        payload.put("growthStage", "成熟期");
        payload.put("location", "湖南省测试村");
        payload.put("latitude", 28.19409D);
        payload.put("longitude", 112.98228D);
        payload.put("photos", "[\"https://example.com/plot.jpg\"]");
        payload.put("remark", "035 地块巡检");
        return payload;
    }

    private Map<String, Object> machinePayload(String name) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("name", name);
        payload.put("model", "久保田 4LZ-4");
        payload.put("width", new BigDecimal("2.20"));
        payload.put("power", new BigDecimal("120.00"));
        payload.put("pricePerHour", new BigDecimal("260.00"));
        payload.put("pricePerMu", new BigDecimal("68.00"));
        payload.put("serviceRadiusKm", 40);
        payload.put("status", 1);
        payload.put("remark", "035 设备巡检");
        return payload;
    }

    private JsonNode readJson(MvcResult result) throws Exception {
        return objectMapper.readTree(result.getResponse().getContentAsString());
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}
