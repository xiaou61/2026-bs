package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PatientProfile {
    private Long id;
    private String patientNo;
    private String patientName;
    private String idCard;
    private String phone;
    private String visitCardNo;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








