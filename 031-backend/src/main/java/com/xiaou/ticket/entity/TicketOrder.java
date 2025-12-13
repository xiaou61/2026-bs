package com.xiaou.ticket.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TicketOrder {
    private Long id;
    private String orderNo;
    private Long userId;
    private Long matchId;
    private BigDecimal totalAmount;
    private BigDecimal paymentAmount;
    private BigDecimal discountAmount;
    private String status;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
