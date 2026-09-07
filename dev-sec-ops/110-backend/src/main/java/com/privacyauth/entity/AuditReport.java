package com.privacyauth.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditReport {
    private Long id;
    private String reportNo;
    private String reportName;
    private String reportPeriod;
    private String generatedBy;
    private String summaryText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
