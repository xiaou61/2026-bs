package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AssistTask {
    private Long id;
    private String taskNo;
    private String volunteerNo;
    private String taskType;
    private String executeTime;
    private String executorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

