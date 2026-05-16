package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamItem {
    private Long id;
    private String itemNo;
    private String itemName;
    private String category;
    private String departmentName;
    private Integer durationMinutes;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








