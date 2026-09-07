package com.xiaou.wedding.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private String orderNo;
    private Long customerId;
    private Long packageId;
    private Long photographerId;
    private Long appointmentId;
    private BigDecimal totalAmount;
    private BigDecimal depositAmount;
    private BigDecimal balanceAmount;
    private BigDecimal paidAmount;
    private String status;
    private String paymentMethod;
    private LocalDate shootingDate;
    private LocalDate photoSelectionDate;
    private LocalDate pickupDate;
    private String remark;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer deleted;
}
