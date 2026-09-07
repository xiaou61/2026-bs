package com.cloudcost.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CostItem {
    private Long id;
    private String resourceId;
    private String resourceName;
    private String resourceType;
    private String namespaceName;
    private String billMonth;
    private BigDecimal costAmount;
    private BigDecimal usageQuantity;
    private String unitName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
