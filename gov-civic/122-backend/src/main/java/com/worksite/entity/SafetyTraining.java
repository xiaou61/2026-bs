package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SafetyTraining {
    private Long id;
    private String trainingNo;
    private String courseName;
    private String trainerName;
    private String teamName;
    private Integer traineeCount;
    private String trainDate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
