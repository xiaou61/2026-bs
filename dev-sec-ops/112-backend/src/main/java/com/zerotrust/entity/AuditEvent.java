package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditEvent {
    private Long id;
    private String eventNo;
    private String eventType;
    private String deviceName;
    private String employeeName;
    private String detailText;
    private LocalDateTime eventTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
