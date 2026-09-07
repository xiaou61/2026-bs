package com.xiaou.wedding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WeddingPhotoStudioApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCompleteCoreManagementFlow() throws Exception {
        String token = login("admin", "admin123");

        JsonNode packageList = readJson(mockMvc.perform(
                get("/package/list")
                        .param("pageNum", "1")
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(packageList.path("code").asInt()).isEqualTo(200);
        assertThat(packageList.path("data").isArray()).isTrue();
        assertThat(packageList.path("data").size()).isGreaterThan(0);

        String customerName = "巡检客户033";
        Map<String, Object> customerPayload = new LinkedHashMap<>();
        customerPayload.put("name", customerName);
        customerPayload.put("gender", 1);
        customerPayload.put("age", 29);
        customerPayload.put("phone", "13900139033");
        customerPayload.put("email", "check033@example.com");
        customerPayload.put("address", "测试地址033");
        customerPayload.put("customerType", "婚纱客户");
        customerPayload.put("source", "巡检");

        JsonNode customerCreate = readJson(mockMvc.perform(
                post("/customer")
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerPayload)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(customerCreate.path("code").asInt()).isEqualTo(200);

        JsonNode customerList = readJson(mockMvc.perform(
                get("/customer/list")
                        .header("Authorization", bearer(token))
                        .param("keyword", customerName))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(customerList.path("code").asInt()).isEqualTo(200);
        JsonNode createdCustomer = customerList.path("data").get(0);
        long customerId = createdCustomer.path("id").asLong();
        assertThat(customerId).isPositive();

        Map<String, Object> appointmentPayload = new LinkedHashMap<>();
        appointmentPayload.put("customerId", customerId);
        appointmentPayload.put("appointmentType", "SHOOTING");
        appointmentPayload.put("packageId", 1);
        appointmentPayload.put("photographerId", 1);
        appointmentPayload.put("studioId", 1);
        appointmentPayload.put("appointmentDate", "2030-01-01");
        appointmentPayload.put("timeSlot", "上午");
        appointmentPayload.put("remark", "033 巡检预约");

        JsonNode appointmentCreate = readJson(mockMvc.perform(
                post("/appointment")
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentPayload)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(appointmentCreate.path("code").asInt()).isEqualTo(200);

        JsonNode appointmentList = readJson(mockMvc.perform(
                get("/appointment/list")
                        .header("Authorization", bearer(token))
                        .param("customerId", String.valueOf(customerId))
                        .param("date", "2030-01-01"))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(appointmentList.path("code").asInt()).isEqualTo(200);
        long appointmentId = appointmentList.path("data").get(0).path("id").asLong();
        assertThat(appointmentId).isPositive();

        Map<String, Object> orderPayload = new LinkedHashMap<>();
        orderPayload.put("customerId", customerId);
        orderPayload.put("packageId", 1);
        orderPayload.put("photographerId", 1);
        orderPayload.put("appointmentId", appointmentId);
        orderPayload.put("totalAmount", 3999.00);
        orderPayload.put("depositAmount", 1000.00);
        orderPayload.put("balanceAmount", 2999.00);
        orderPayload.put("paymentMethod", "CASH");
        orderPayload.put("shootingDate", "2030-01-01");
        orderPayload.put("remark", "033 巡检订单");

        JsonNode orderCreate = readJson(mockMvc.perform(
                post("/order")
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderPayload)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(orderCreate.path("code").asInt()).isEqualTo(200);

        JsonNode orderList = readJson(mockMvc.perform(
                get("/order/list")
                        .header("Authorization", bearer(token))
                        .param("customerId", String.valueOf(customerId)))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(orderList.path("code").asInt()).isEqualTo(200);
        JsonNode createdOrder = orderList.path("data").get(0);
        long orderId = createdOrder.path("id").asLong();
        assertThat(orderId).isPositive();

        JsonNode orderStatus = readJson(mockMvc.perform(
                put("/order/{id}/status", orderId)
                        .header("Authorization", bearer(token))
                        .param("status", "PAID")
                        .param("paidAmount", "3999.00"))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(orderStatus.path("code").asInt()).isEqualTo(200);

        JsonNode dashboard = readJson(mockMvc.perform(
                get("/stats/dashboard")
                        .header("Authorization", bearer(token))
                        .param("date", "2030-01-01"))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(dashboard.path("code").asInt()).isEqualTo(200);
        assertThat(dashboard.path("data").path("customerCount").asInt()).isGreaterThanOrEqualTo(3);
        assertThat(dashboard.path("data").path("orderCount").asInt()).isGreaterThanOrEqualTo(1);
        assertThat(dashboard.path("data").path("appointmentCount").asInt()).isGreaterThanOrEqualTo(1);
        assertThat(dashboard.path("data").path("orderAmount").decimalValue()).isGreaterThanOrEqualTo(new java.math.BigDecimal("3999.00"));
    }

    private String login(String username, String password) throws Exception {
        Map<String, String> payload = Map.of(
                "username", username,
                "password", password
        );
        JsonNode loginResponse = readJson(mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andReturn());

        assertThat(loginResponse.path("code").asInt()).isEqualTo(200);
        String token = loginResponse.path("data").path("token").asText();
        assertThat(token).isNotBlank();
        return token;
    }

    private JsonNode readJson(MvcResult result) throws Exception {
        return objectMapper.readTree(result.getResponse().getContentAsString());
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}
