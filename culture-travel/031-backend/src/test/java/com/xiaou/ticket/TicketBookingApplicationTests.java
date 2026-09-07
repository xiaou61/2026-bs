package com.xiaou.ticket;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketBookingApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCompleteOrderAndTicketFlow() throws Exception {
        JsonNode loginNode = readJson(mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "user1",
                                  "password": "user123"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn());

        assertEquals(200, loginNode.get("code").asInt());
        String token = loginNode.at("/data/token").asText();
        Long userId = loginNode.at("/data/user/id").asLong();
        assertNotNull(token);

        JsonNode upcomingNode = readJson(mockMvc.perform(get("/matches/upcoming"))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(200, upcomingNode.get("code").asInt());
        assertFalse(upcomingNode.withArray("data").isEmpty());

        Long matchId = upcomingNode.at("/data/0/id").asLong();
        JsonNode pricingNode = readJson(mockMvc.perform(get("/matches/{id}/pricing", matchId))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(200, pricingNode.get("code").asInt());
        Long pricingId = pricingNode.at("/data/0/id").asLong();
        Long categoryId = pricingNode.at("/data/0/categoryId").asLong();

        JsonNode seatNode = readJson(mockMvc.perform(get("/matches/{id}/seats", matchId)
                        .param("categoryId", String.valueOf(categoryId)))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(200, seatNode.get("code").asInt());

        List<Long> seatIds = seatNode.withArray("data").findValuesAsText("id").stream()
                .map(Long::valueOf)
                .limit(2)
                .toList();
        assertFalse(seatIds.isEmpty());

        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("matchId", matchId);
        orderRequest.put("pricingId", pricingId);
        orderRequest.put("seatIds", seatIds);

        JsonNode orderNode = readJson(mockMvc.perform(post("/orders")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(200, orderNode.get("code").asInt());
        Long orderId = orderNode.at("/data/id").asLong();

        JsonNode payNode = readJson(mockMvc.perform(post("/orders/{id}/pay", orderId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "paymentMethod": "BALANCE"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(200, payNode.get("code").asInt());
        assertEquals("PAID", payNode.at("/data/status").asText());

        JsonNode orderListNode = readJson(mockMvc.perform(get("/orders/user/{userId}", userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(200, orderListNode.get("code").asInt());
        assertFalse(orderListNode.withArray("data").isEmpty());

        JsonNode ticketsNode = readJson(mockMvc.perform(get("/orders/{id}/tickets", orderId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(200, ticketsNode.get("code").asInt());
        assertEquals(seatIds.size(), ticketsNode.withArray("data").size());
    }

    @Test
    void shouldRejectOrderQueryWithoutToken() throws Exception {
        mockMvc.perform(get("/orders/user/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldRefundBalanceAndCancelTicketsWhenPaidOrderIsCancelled() throws Exception {
        JsonNode loginNode = readJson(mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "user1",
                                  "password": "user123"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn());

        String token = loginNode.at("/data/token").asText();
        Long userId = loginNode.at("/data/user/id").asLong();

        JsonNode balanceBeforeNode = readJson(mockMvc.perform(get("/users/{id}/balance", userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn());
        BigDecimal balanceBefore = balanceBeforeNode.get("data").decimalValue();

        JsonNode upcomingNode = readJson(mockMvc.perform(get("/matches/upcoming"))
                .andExpect(status().isOk())
                .andReturn());
        Long matchId = upcomingNode.at("/data/0/id").asLong();

        JsonNode pricingNode = readJson(mockMvc.perform(get("/matches/{id}/pricing", matchId))
                .andExpect(status().isOk())
                .andReturn());
        Long pricingId = pricingNode.at("/data/0/id").asLong();
        Long categoryId = pricingNode.at("/data/0/categoryId").asLong();
        BigDecimal ticketPrice = pricingNode.at("/data/0/price").decimalValue();

        JsonNode seatNode = readJson(mockMvc.perform(get("/matches/{id}/seats", matchId)
                        .param("categoryId", String.valueOf(categoryId)))
                .andExpect(status().isOk())
                .andReturn());
        List<Long> seatIds = seatNode.withArray("data").findValuesAsText("id").stream()
                .map(Long::valueOf)
                .limit(1)
                .toList();

        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("matchId", matchId);
        orderRequest.put("pricingId", pricingId);
        orderRequest.put("seatIds", seatIds);

        JsonNode orderNode = readJson(mockMvc.perform(post("/orders")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andReturn());
        Long orderId = orderNode.at("/data/id").asLong();

        mockMvc.perform(post("/orders/{id}/pay", orderId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "paymentMethod": "BALANCE"
                                }
                                """))
                .andExpect(status().isOk());

        JsonNode balanceAfterPayNode = readJson(mockMvc.perform(get("/users/{id}/balance", userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(balanceBefore.subtract(ticketPrice), balanceAfterPayNode.get("data").decimalValue());

        JsonNode cancelNode = readJson(mockMvc.perform(delete("/orders/{id}", orderId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(200, cancelNode.get("code").asInt());

        JsonNode balanceAfterCancelNode = readJson(mockMvc.perform(get("/users/{id}/balance", userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn());
        assertEquals(balanceBefore, balanceAfterCancelNode.get("data").decimalValue());

        JsonNode ticketsAfterCancelNode = readJson(mockMvc.perform(get("/orders/{id}/tickets", orderId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn());
        List<String> ticketStatuses = ticketsAfterCancelNode.withArray("data").findValuesAsText("status");
        assertEquals(List.of("CANCELLED"), ticketStatuses);
    }

    private JsonNode readJson(MvcResult result) throws Exception {
        return objectMapper.readTree(result.getResponse().getContentAsString());
    }
}
