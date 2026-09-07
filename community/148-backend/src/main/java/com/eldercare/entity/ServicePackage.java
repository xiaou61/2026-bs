package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServicePackage {
    private Long id;
    private String packageNo;
    private String packageName;
    private String serviceCategory;
    private String targetGroup;
    private String serviceCycle;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
