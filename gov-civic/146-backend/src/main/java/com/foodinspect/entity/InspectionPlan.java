package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InspectionPlan {
    private Long id;
    private String planNo;
    private String planName;
    private String inspectionType;
    private String regionName;
    private String inspectionCycle;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






