package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ServiceCheckin {
    private Long id;
    private String checkinNo;
    private String volunteerNo;
    private String checkinLocation;
    private String checkinTime;
    private String checkinName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

