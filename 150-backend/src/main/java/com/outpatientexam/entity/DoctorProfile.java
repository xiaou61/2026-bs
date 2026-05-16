package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DoctorProfile {
    private Long id;
    private String doctorNo;
    private String doctorName;
    private String phone;
    private String departmentName;
    private String titleLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








