package com.xiaou.seniorhealth;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SeniorHealthApplicationTests {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldLoginWithSeededAdminAndParseTokenClaims() throws Exception {
        JsonNode loginNode = login("admin", "admin123");
        assertEquals(0, loginNode.get("code").asInt());
        assertEquals("ADMIN", loginNode.at("/data/role").asText());

        String token = loginNode.at("/data/token").asText();
        assertNotNull(token);
        assertFalse(token.isBlank());

        JsonNode meNode = readJson(mockMvc.perform(get("/api/auth/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn());

        assertEquals(0, meNode.get("code").asInt());
        assertEquals("admin", meNode.at("/data/sub").asText());
        assertEquals("ADMIN", meNode.at("/data/role").asText());
    }

    @Test
    void shouldRejectPrivilegedRegistrationAndExposeSeededElderSelfService() throws Exception {
        JsonNode registerNode = readJson(mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", "forbidden_admin",
                                "password", "admin123",
                                "role", "ADMIN"
                        ))))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(-1, registerNode.get("code").asInt());

        JsonNode elderLoginNode = login("elder1", "elder123");
        assertEquals(0, elderLoginNode.get("code").asInt());
        String elderToken = elderLoginNode.at("/data/token").asText();

        mockMvc.perform(get("/api/elders")
                        .header("Authorization", "Bearer " + elderToken))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/stats/overview")
                        .header("Authorization", "Bearer " + elderToken))
                .andExpect(status().isForbidden());

        JsonNode elderProfileNode = readJson(mockMvc.perform(get("/api/elders/me")
                        .header("Authorization", "Bearer " + elderToken))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, elderProfileNode.get("code").asInt());
        assertEquals("13888880001", elderProfileNode.at("/data/phone").asText());

        JsonNode measurementNode = readJson(mockMvc.perform(get("/api/measurements/me/latest")
                        .header("Authorization", "Bearer " + elderToken)
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, measurementNode.get("code").asInt());
        assertFalse(measurementNode.withArray("data").isEmpty());

        JsonNode assessmentNode = readJson(mockMvc.perform(get("/api/assessment/me")
                        .header("Authorization", "Bearer " + elderToken))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, assessmentNode.get("code").asInt());
        assertEquals("HIGH", assessmentNode.at("/data/risk").asText());

        JsonNode appointmentNode = readJson(mockMvc.perform(get("/api/appointments/me")
                        .header("Authorization", "Bearer " + elderToken))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, appointmentNode.get("code").asInt());
        assertFalse(appointmentNode.withArray("data").isEmpty());

        JsonNode followUpNode = readJson(mockMvc.perform(get("/api/followups/me")
                        .header("Authorization", "Bearer " + elderToken))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, followUpNode.get("code").asInt());
        assertFalse(followUpNode.withArray("data").isEmpty());

        JsonNode notificationNode = readJson(mockMvc.perform(get("/api/notifications")
                        .header("Authorization", "Bearer " + elderToken))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, notificationNode.get("code").asInt());
        assertFalse(notificationNode.withArray("data").isEmpty());
    }

    @Test
    void shouldCompleteCoreHealthManagementFlow() throws Exception {
        JsonNode adminLoginNode = login("admin", "admin123");
        String adminToken = adminLoginNode.at("/data/token").asText();

        String doctorUsername = "doctor_" + System.nanoTime();
        JsonNode registerDoctorNode = readJson(mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", doctorUsername,
                                "password", "doctor123",
                                "role", "DOCTOR"
                        ))))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, registerDoctorNode.get("code").asInt());

        JsonNode usersNode = readJson(mockMvc.perform(get("/api/admin/users")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, usersNode.get("code").asInt());
        JsonNode users = usersNode.withArray("data");
        assertFalse(users.isEmpty());
        assertFalse(users.get(0).has("password"));

        long doctorUserId = -1L;
        for (JsonNode userNode : users) {
            if (doctorUsername.equals(userNode.path("username").asText())) {
                doctorUserId = userNode.path("id").asLong();
                break;
            }
        }
        assertTrue(doctorUserId > 0);

        String elderPhone = "139" + String.valueOf(System.nanoTime()).substring(6, 14);
        JsonNode elderNode = readJson(mockMvc.perform(post("/api/elders")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "name", "王阿姨",
                                "gender", "FEMALE",
                                "birthDate", "1950-01-01",
                                "phone", elderPhone,
                                "address", "社区一号楼",
                                "emergencyContact", "王叔叔 13800000000"
                        ))))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, elderNode.get("code").asInt());
        long elderId = elderNode.at("/data/id").asLong();

        createMeasurement(adminToken, elderId, "WEIGHT", 68.0, null, "kg");
        createMeasurement(adminToken, elderId, "HEIGHT", 165.0, null, "cm");
        createMeasurement(adminToken, elderId, "BP", 145.0, 92.0, "mmHg");

        JsonNode measurementNode = readJson(mockMvc.perform(get("/api/measurements/elder/{elderId}/latest", elderId)
                        .header("Authorization", "Bearer " + adminToken)
                        .param("size", "10")
                        .param("type", "BP"))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, measurementNode.get("code").asInt());
        assertEquals(1, measurementNode.withArray("data").size());

        JsonNode assessmentNode = readJson(mockMvc.perform(get("/api/assessment/elder/{elderId}", elderId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, assessmentNode.get("code").asInt());
        assertEquals("HIGH", assessmentNode.at("/data/bpStatus").asText());
        assertEquals("HIGH", assessmentNode.at("/data/risk").asText());
        assertTrue(assessmentNode.at("/data/bmi").asDouble() > 24.0);

        String appointmentTime = LocalDateTime.now().plusDays(1).withSecond(0).withNano(0).format(DATE_TIME_FORMATTER);
        JsonNode appointmentNode = readJson(mockMvc.perform(post("/api/appointments")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "elderId", elderId,
                                "doctorUserId", doctorUserId,
                                "startTime", appointmentTime,
                                "reason", "高血压复诊"
                        ))))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, appointmentNode.get("code").asInt());
        long appointmentId = appointmentNode.at("/data/id").asLong();

        JsonNode followUpNode = readJson(mockMvc.perform(post("/api/followups")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "elderId", elderId,
                                "doctorUserId", doctorUserId,
                                "type", "TEL",
                                "dueDate", LocalDate.now().plusDays(3).toString(),
                                "note", "复测血压"
                        ))))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, followUpNode.get("code").asInt());
        long followUpId = followUpNode.at("/data/id").asLong();

        JsonNode appointmentsByElderNode = readJson(mockMvc.perform(get("/api/appointments/elder/{elderId}", elderId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, appointmentsByElderNode.get("code").asInt());
        assertEquals(1, appointmentsByElderNode.withArray("data").size());

        JsonNode appointmentsByDoctorNode = readJson(mockMvc.perform(get("/api/appointments/doctor/{doctorUserId}/upcoming", doctorUserId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, appointmentsByDoctorNode.get("code").asInt());
        assertEquals(1, appointmentsByDoctorNode.withArray("data").size());

        JsonNode appointmentStatusNode = readJson(mockMvc.perform(post("/api/appointments/{id}/status", appointmentId)
                        .header("Authorization", "Bearer " + adminToken)
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, appointmentStatusNode.get("code").asInt());
        assertEquals("COMPLETED", appointmentStatusNode.at("/data/status").asText());

        String from = LocalDate.now().toString();
        String to = LocalDate.now().plusDays(7).toString();
        JsonNode followUpRangeNode = readJson(mockMvc.perform(get("/api/followups/doctor/{doctorUserId}/range", doctorUserId)
                        .header("Authorization", "Bearer " + adminToken)
                        .param("from", from)
                        .param("to", to))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, followUpRangeNode.get("code").asInt());
        assertEquals(1, followUpRangeNode.withArray("data").size());

        JsonNode followUpStatusNode = readJson(mockMvc.perform(post("/api/followups/{id}/status", followUpId)
                        .header("Authorization", "Bearer " + adminToken)
                        .param("status", "DONE"))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, followUpStatusNode.get("code").asInt());
        assertEquals("DONE", followUpStatusNode.at("/data/status").asText());

        JsonNode statsNode = readJson(mockMvc.perform(get("/api/stats/overview")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, statsNode.get("code").asInt());
        assertTrue(statsNode.at("/data/users").asLong() >= 4);
        assertTrue(statsNode.at("/data/elders").asLong() >= 1);
        assertTrue(statsNode.at("/data/upcomingAppointments").asLong() >= 0);
    }

    private void createMeasurement(String token, long elderId, String type, Double value1, Double value2, String unit) throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("elderId", elderId);
        payload.put("type", type);
        payload.put("value1", value1);
        payload.put("value2", value2);
        payload.put("unit", unit);
        payload.put("measuredAt", LocalDateTime.now().withSecond(0).withNano(0).format(DATE_TIME_FORMATTER));

        JsonNode measurementNode = readJson(mockMvc.perform(post("/api/measurements")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(0, measurementNode.get("code").asInt());
    }

    private JsonNode login(String username, String password) throws Exception {
        return readJson(mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", username,
                                "password", password
                        ))))
                .andExpect(status().isOk())
                .andReturn());
    }

    private JsonNode readJson(MvcResult result) throws Exception {
        return objectMapper.readTree(result.getResponse().getContentAsString());
    }
}
