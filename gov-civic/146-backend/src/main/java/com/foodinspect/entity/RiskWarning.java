package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RiskWarning {
    private Long id;
    private String warningNo;
    private String foodName;
    private String warningTime;
    private String riskType;
    private String disposalSuggestion;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






