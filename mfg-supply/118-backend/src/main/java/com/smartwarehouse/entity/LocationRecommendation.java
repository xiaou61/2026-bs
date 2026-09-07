package com.smartwarehouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LocationRecommendation {
    private Long id;
    private String recommendNo;
    private String itemName;
    private String locationNo;
    private BigDecimal matchScore;
    private String reasonText;
    private String operatorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
