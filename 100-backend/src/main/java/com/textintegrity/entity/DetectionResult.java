package com.textintegrity.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DetectionResult {
    private Long id;
    private Long taskId;
    private Long submissionId;
    private String matchedRules;
    private BigDecimal aiProbability;
    private BigDecimal repeatRate;
    private BigDecimal citationRisk;
    private String riskLevel;
    private BigDecimal score;
    private String conclusion;
    private String suggestion;
    private Integer reviewStatus;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
