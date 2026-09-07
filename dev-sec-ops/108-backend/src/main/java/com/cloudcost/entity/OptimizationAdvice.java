package com.cloudcost.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OptimizationAdvice {
    private Long id;
    private String adviceNo;
    private String resourceName;
    private String adviceType;
    private BigDecimal expectedSaving;
    private String ownerName;
    private LocalDateTime generatedTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
