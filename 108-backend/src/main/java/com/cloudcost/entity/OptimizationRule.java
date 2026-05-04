package com.cloudcost.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OptimizationRule {
    private Long id;
    private String ruleName;
    private String resourceType;
    private String metricName;
    private BigDecimal thresholdValue;
    private String actionType;
    private Integer priority;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
