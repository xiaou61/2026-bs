package com.smartwarehouse.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AgvTask {
    private Long id;
    private String taskNo;
    private String taskType;
    private String agvNo;
    private String sourceLocation;
    private String targetLocation;
    private String priorityLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
