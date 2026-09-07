package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlertEvent {
    private Long id;
    private String alertNo;
    private String elderName;
    private String reportTime;
    private String alertType;
    private String handlingSuggestion;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
