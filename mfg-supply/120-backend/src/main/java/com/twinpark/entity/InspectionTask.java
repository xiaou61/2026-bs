package com.twinpark.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InspectionTask {
    private Long id;
    private String taskNo;
    private String routeName;
    private String inspectorName;
    private String planTime;
    private String priorityLevel;
    private Integer pointTotal;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
