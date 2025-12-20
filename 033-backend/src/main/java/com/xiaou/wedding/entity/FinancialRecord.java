package com.xiaou.wedding.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FinancialRecord {
    private Long id;
    private String recordType;
    private Long orderId;
    private BigDecimal amount;
    private String paymentMethod;
    private String category;
    private String description;
    private LocalDate recordDate;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer deleted;
}
