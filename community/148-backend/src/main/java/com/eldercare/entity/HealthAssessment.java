package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HealthAssessment {
    private Long id;
    private String assessmentNo;
    private String elderName;
    private String assessmentItem;
    private String assessmentTime;
    private String assessorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
