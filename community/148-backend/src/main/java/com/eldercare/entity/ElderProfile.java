package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ElderProfile {
    private Long id;
    private String elderNo;
    private String elderName;
    private String ageGroup;
    private String homeAddress;
    private String careLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
