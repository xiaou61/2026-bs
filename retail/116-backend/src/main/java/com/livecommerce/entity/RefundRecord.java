package com.livecommerce.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RefundRecord {
    private Long id;
    private String refundNo;
    private String ticketNo;
    private String orderNo;
    private BigDecimal refundAmount;
    private String reasonText;
    private String auditUser;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
