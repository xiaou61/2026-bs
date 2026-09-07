package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamReport {
    private Long id;
    private String reportNo;
    private String patientName;
    private String conclusion;
    private String completedTime;
    private String technicianName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








