package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FertilizerPlan {
    private Long id;
    private String planNo;
    private String greenhouseNo;
    private String fertilizerType;
    private BigDecimal planAmount;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
