package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RevisitAdvice {
    private Long id;
    private String adviceNo;
    private String patientName;
    private String adviceSubject;
    private String adviceType;
    private String adviceTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








