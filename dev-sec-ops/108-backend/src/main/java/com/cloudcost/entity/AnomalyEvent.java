package com.cloudcost.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AnomalyEvent {
    private Long id;
    private String eventNo;
    private String accountName;
    private String resourceName;
    private String anomalyType;
    private BigDecimal costAmount;
    private LocalDateTime detectedTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
