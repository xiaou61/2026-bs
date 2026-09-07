package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InspectionTask {
    private Long id;
    private String taskNo;
    private String planNo;
    private String inspectorName;
    private String checkArea;
    private String priorityLevel;
    private String deadlineTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
