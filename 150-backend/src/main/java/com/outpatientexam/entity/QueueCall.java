package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QueueCall {
    private Long id;
    private String queueNo;
    private String patientName;
    private String callTime;
    private String waitingStatus;
    private String roomNo;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








