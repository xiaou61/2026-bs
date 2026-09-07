package com.cloudcost.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CostAllocation {
    private Long id;
    private String allocationNo;
    private String namespaceName;
    private String businessLine;
    private String ownerName;
    private BigDecimal allocatedAmount;
    private String allocationMonth;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
