package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicationReminder {
    private Long id;
    private String reminderNo;
    private String elderName;
    private String reminderType;
    private String reminderTime;
    private String receiverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
