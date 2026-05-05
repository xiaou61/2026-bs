package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaintenanceTicket {
    private Long id;
    private String ticketNo;
    private String homeNo;
    private String deviceName;
    private String issueType;
    private String maintainerName;
    private String appointmentTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
