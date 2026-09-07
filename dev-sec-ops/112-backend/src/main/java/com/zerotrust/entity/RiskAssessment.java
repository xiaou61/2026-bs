package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RiskAssessment {
    private Long id;
    private String assessmentNo;
    private String deviceName;
    private String employeeName;
    private Integer riskScore;
    private String riskLevel;
    private String adviceText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
