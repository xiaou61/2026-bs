package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RiskWarning {
    private Long id;
    private String warningNo;
    private String warningType;
    private String subjectName;
    private String riskLevel;
    private String ownerName;
    private LocalDateTime detectedTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
