package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckinRecord {
    private Long id;
    private String checkinNo;
    private String patientName;
    private String checkinMethod;
    private String checkinWindow;
    private String checkinTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








