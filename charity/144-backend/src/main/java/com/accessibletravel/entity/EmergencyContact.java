package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmergencyContact {
    private Long id;
    private String contactNo;
    private String travelerNo;
    private String contactName;
    private String contactPhone;
    private String relationRemark;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

