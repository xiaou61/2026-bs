package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class PaymentRecord {
    private Long id;
    private String paymentNo;
    private String sessionNo;
    private String ownerName;
    private BigDecimal payAmount;
    private String payMethod;
    private String payTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
