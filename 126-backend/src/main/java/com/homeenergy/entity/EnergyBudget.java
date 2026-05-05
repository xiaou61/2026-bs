package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class EnergyBudget {
    private Long id;
    private String budgetNo;
    private String homeNo;
    private String budgetMonth;
    private BigDecimal budgetKwh;
    private BigDecimal budgetAmount;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
