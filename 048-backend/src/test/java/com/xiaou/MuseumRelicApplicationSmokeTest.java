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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MuseumRelicApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldStartWithDemoDataAndSupportCoreMuseumFlows() throws Exception {
        String adminToken = extractToken(login("admin", "123456")
                .andExpect(jsonPath("$.data.role").value(3))
                .andReturn());

        mockMvc.perform(get("/api/notice/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(3)));

        mockMvc.perform(get("/api/admin/statistics")
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.relicCount").value(greaterThanOrEqualTo(8)))
                .andExpect(jsonPath("$.data.exhibitionCount").value(greaterThanOrEqualTo(3)));

        String userToken = extractToken(login("user1", "123456")
                .andExpect(jsonPath("$.data.role").value(0))
                .andReturn());

        mockMvc.perform(get("/api/base/categories")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(8)));

        mockMvc.perform(get("/api/base/dynasties")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(10)));

        mockMvc.perform(get("/api/base/halls")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(6)));

        mockMvc.perform(get("/api/base/guides")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(2)));

        mockMvc.perform(get("/api/relic/list")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(8)));

        mockMvc.perform(get("/api/relic/1")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("四羊方尊"));

        mockMvc.perform(get("/api/relic/1/images")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(2)));

        mockMvc.perform(post("/api/relic/1/like")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/exhibition/list")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(3)));

        mockMvc.perform(get("/api/research/list")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(2)));

        mockMvc.perform(post("/api/reservation")
                        .header("Authorization", bearer(userToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "exhibitionId", 1,
                                "visitDate", LocalDate.now().plusDays(1).toString(),
                                "timeSlot", "09:00-12:00",
                                "visitorCount", 2,
                                "contactName", "自动化游客",
                                "contactPhone", "13800000005",
                                "totalPrice", new BigDecimal("100.00"),
                                "remark", "自动化巡检参观预约"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        Long reservationId = extractReservationIdByContact(userToken, "自动化游客");
        mockMvc.perform(post("/api/admin/reservation/" + reservationId + "/confirm")
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(post("/api/admin/reservation/" + reservationId + "/complete")
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        assertReservationStatus(userToken, reservationId, 2);

        mockMvc.perform(post("/api/user/favorite")
                        .header("Authorization", bearer(userToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("relicId", 8))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/user/favorites")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(1)));

        mockMvc.perform(delete("/api/user/favorite/8")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(post("/api/guide-booking")
                        .header("Authorization", bearer(userToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "guideId", 2,
                                "visitDate", LocalDate.now().plusDays(2).toString(),
                                "startTime", "10:00:00",
                                "duration", 90,
                                "visitorCount", 2,
                                "language", "中文",
                                "price", new BigDecimal("200.00"),
                                "remark", "自动化巡检讲解预约"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        Long guideBookingId = extractGuideBookingIdByRemark(userToken, "自动化巡检讲解预约");
        String guideToken = extractToken(login("guide1", "123456")
                .andExpect(jsonPath("$.data.role").value(1))
                .andReturn());

        mockMvc.perform(get("/api/guide/bookings")
                        .header("Authorization", bearer(guideToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(1)));

        mockMvc.perform(post("/api/guide/booking/" + guideBookingId + "/confirm")
                        .header("Authorization", bearer(guideToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(post("/api/guide/booking/" + guideBookingId + "/complete")
                        .header("Authorization", bearer(guideToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        assertGuideBookingStatus(userToken, guideBookingId, 3);

        String researcherToken = extractToken(login("researcher1", "123456")
                .andExpect(jsonPath("$.data.role").value(2))
                .andReturn());

        mockMvc.perform(post("/api/researcher/research")
                        .header("Authorization", bearer(researcherToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "title", "自动化巡检文物研究",
                                "relicId", 1,
                                "content", "用于验证研究员提交与管理员审核链路。",
                                "summary", "自动化巡检摘要",
                                "fileUrl", "/files/research/auto.pdf"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        Long researchId = extractResearchIdByTitle(researcherToken, "自动化巡检文物研究");
        mockMvc.perform(put("/api/admin/research/" + researchId + "/audit")
                        .header("Authorization", bearer(adminToken))
                        .param("status", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/research/list")
                        .header("Authorization", bearer(userToken))
                        .param("keyword", "自动化巡检文物研究"))
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
        throw new AssertionError("未找到参观预约：" + contactName);
    }

    private Long extractGuideBookingIdByRemark(String token, String remark) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/guide-booking/my")
                        .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();
        JsonNode records = readJson(result).path("data").path("records");
        for (JsonNode record : records) {
            if (remark.equals(record.path("remark").asText())) {
                return record.path("id").asLong();
            }
        }
        throw new AssertionError("未找到讲解预约：" + remark);
    }

    private Long extractResearchIdByTitle(String token, String title) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/researcher/researches")
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
        throw new AssertionError("未找到研究成果：" + title);
    }

    private void assertReservationStatus(String token, Long reservationId, int status) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/reservation/my")
                        .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();
        JsonNode records = readJson(result).path("data").path("records");
        for (JsonNode record : records) {
            if (record.path("id").asLong() == reservationId) {
                org.junit.jupiter.api.Assertions.assertEquals(status, record.path("status").asInt());
                return;
            }
        }
        throw new AssertionError("未找到参观预约 ID：" + reservationId);
    }

    private void assertGuideBookingStatus(String token, Long bookingId, int status) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/guide-booking/my")
                        .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();
        JsonNode records = readJson(result).path("data").path("records");
        for (JsonNode record : records) {
            if (record.path("id").asLong() == bookingId) {
                org.junit.jupiter.api.Assertions.assertEquals(status, record.path("status").asInt());
                return;
            }
        }
        throw new AssertionError("未找到讲解预约 ID：" + bookingId);
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
