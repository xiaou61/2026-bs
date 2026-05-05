package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConstructionProject {
    private Long id;
    private String projectNo;
    private String projectName;
    private String locationName;
    private String contractorName;
    private String startDate;
    private String riskLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
