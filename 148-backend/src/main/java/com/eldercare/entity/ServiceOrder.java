package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceOrder {
    private Long id;
    private String orderNo;
    private String serviceSubject;
    private String serviceAddress;
    private String visitTime;
    private String dispatcherName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
