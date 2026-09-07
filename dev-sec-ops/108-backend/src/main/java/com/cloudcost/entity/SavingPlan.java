package com.cloudcost.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SavingPlan {
    private Long id;
    private String planName;
    private String accountName;
    private String coverageScope;
    private BigDecimal commitAmount;
    private BigDecimal expectedSaving;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
