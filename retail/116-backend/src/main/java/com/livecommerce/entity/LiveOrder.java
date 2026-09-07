package com.livecommerce.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LiveOrder {
    private Long id;
    private String orderNo;
    private String sessionTitle;
    private String productName;
    private String buyerName;
    private BigDecimal orderAmount;
    private String payChannel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
