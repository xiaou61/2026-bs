package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpirationReminder {
    private Long id;
    private String reminderNo;
    private String contractTitle;
    private String counterpartyName;
    private String expiryDate;
    private String reminderMethod;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



