package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SamplingTask {
    private Long id;
    private String taskNo;
    private String taskTitle;
    private String samplingLocation;
    private String samplingTime;
    private String inspectorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






