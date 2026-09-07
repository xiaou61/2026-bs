package com.cloudcost.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CloudAccount {
    private Long id;
    private String accountName;
    private String accountCode;
    private String provider;
    private String ownerName;
    private String accessMode;
    private BigDecimal monthlyBudget;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
