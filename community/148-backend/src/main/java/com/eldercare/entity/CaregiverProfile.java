package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaregiverProfile {
    private Long id;
    private String caregiverNo;
    private String caregiverName;
    private String phoneNumber;
    private String caregiverLevel;
    private String organizationName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
