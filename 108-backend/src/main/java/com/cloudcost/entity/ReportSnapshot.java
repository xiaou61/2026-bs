package com.cloudcost.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReportSnapshot {
    private Long id;
    private String reportName;
    private String reportMonth;
    private String reportType;
    private String generatedBy;
    private String summaryText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
