package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AbnormalAlert {
    private Long id;
    private String alertNo;
    private String patientName;
    private String alertType;
    private String alertTime;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








