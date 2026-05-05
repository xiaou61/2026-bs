package com.twinpark.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DefectReport {
    private Long id;
    private String defectNo;
    private String deviceName;
    private String severityLevel;
    private String defectDesc;
    private String reporterName;
    private String suggestion;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
