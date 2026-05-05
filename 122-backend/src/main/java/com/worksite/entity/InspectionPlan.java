package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InspectionPlan {
    private Long id;
    private String planNo;
    private String projectName;
    private String planDate;
    private String inspectorName;
    private String areaName;
    private String riskFocus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
