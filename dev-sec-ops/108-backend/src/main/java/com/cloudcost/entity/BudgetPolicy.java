package com.cloudcost.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BudgetPolicy {
    private Long id;
    private String policyName;
    private String namespaceName;
    private BigDecimal budgetAmount;
    private BigDecimal alertPercent;
    private String ownerName;
    private String cycleType;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
