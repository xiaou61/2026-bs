package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MaintenanceTicket {
    private Long id;
    private String ticketNo;
    private String deviceNo;
    private String issueType;
    private String ownerName;
    private String deadlineTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
