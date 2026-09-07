package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FaultReport {
    private Long id;
    private String faultNo;
    private String pileNo;
    private String faultType;
    private String severityLevel;
    private String reporterName;
    private String reportTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
