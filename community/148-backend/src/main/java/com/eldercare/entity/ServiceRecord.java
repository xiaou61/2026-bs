package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceRecord {
    private Long id;
    private String recordNo;
    private String elderName;
    private String serviceConclusion;
    private String serviceTime;
    private String caregiverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
