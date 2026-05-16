package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamDepartment {
    private Long id;
    private String departmentNo;
    private String departmentName;
    private String deviceName;
    private String openTime;
    private String waitingArea;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








