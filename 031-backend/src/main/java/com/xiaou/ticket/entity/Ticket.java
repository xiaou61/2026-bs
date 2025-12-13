package com.xiaou.ticket.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Ticket {
    private Long id;
    private String ticketNo;
    private Long orderId;
    private Long matchId;
    private Long seatId;
    private Long categoryId;
    private BigDecimal price;
    private String status;
    private String qrCode;
    private LocalDateTime checkInTime;
    private LocalDateTime createdAt;
}
