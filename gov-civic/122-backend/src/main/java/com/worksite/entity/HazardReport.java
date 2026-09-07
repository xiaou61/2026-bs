package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HazardReport {
    private Long id;
    private String hazardNo;
    private String projectName;
    private String hazardType;
    private String severityLevel;
    private String reporterName;
    private String reportTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
