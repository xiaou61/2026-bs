package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HospitalNotice {
    private Long id;
    private String noticeNo;
    private String patientName;
    private String noticeType;
    private String noticeContent;
    private String receiverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








