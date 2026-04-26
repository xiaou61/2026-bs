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
class HomestayBookingApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldStartWithDemoDataAndSupportCoreBookingFlow() throws Exception {
        login("admin", "admin123")
                .andExpect(jsonPath("$.data.role").value(2))
                .andExpect(jsonPath("$.data.nickname").value("管理员"));

        mockMvc.perform(get("/api/homestay/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.data.records[0].status").value(1));

        mockMvc.perform(get("/api/homestay/detail/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.homestay.name").value("山水间精品民宿"))
                .andExpect(jsonPath("$.data.roomTypes.length()").value(greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.data.facilities.length()").value(greaterThanOrEqualTo(1)));

        String guestToken = extractToken(login("guest1", "123456").andReturn());

        mockMvc.perform(post("/api/favorite/add/1")
                        .header("Authorization", "Bearer " + guestToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/favorite/check/1")
                        .header("Authorization", "Bearer " + guestToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value(true));

        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(2);
        mockMvc.perform(post("/api/booking/create")
                        .header("Authorization", "Bearer " + guestToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "homestayId", 1,
                                "roomTypeId", 1,
                                "checkInDate", checkIn.toString(),
                                "checkOutDate", checkOut.toString(),
                                "guests", 2,
                                "contactName", "测试用户",
                                "contactPhone", "13800138002",
                                "remark", "希望安排安静房间"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        Long bookingId = extractFirstBookingId(guestToken);

        mockMvc.perform(get("/api/booking/detail/" + bookingId)
                        .header("Authorization", "Bearer " + guestToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.status").value(1))
                .andExpect(jsonPath("$.data.totalPrice").value(776.0));

        String hostToken = extractToken(login("host1", "123456").andReturn());
        mockMvc.perform(get("/api/host/homestay/list")
                        .header("Authorization", "Bearer " + hostToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(1)));

        mockMvc.perform(post("/api/host/booking/confirm/" + bookingId)
                        .header("Authorization", "Bearer " + hostToken))
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

    private Long extractFirstBookingId(String token) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/booking/my")
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
