package com.cloudcost.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IdleResource {
    private Long id;
    private String resourceName;
    private String resourceType;
    private String accountName;
    private String ownerName;
    private Integer idleDays;
    private BigDecimal monthlyCost;
    private LocalDateTime detectedTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
