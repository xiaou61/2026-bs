package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDelivery {
    private Long id;
    private String deliveryNo;
    private String patientName;
    private String deliveryMethod;
    private String deliveryTime;
    private String receiverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








