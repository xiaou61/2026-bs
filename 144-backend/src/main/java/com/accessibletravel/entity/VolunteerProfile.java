package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VolunteerProfile {
    private Long id;
    private String volunteerNo;
    private String volunteerName;
    private String serviceExpertise;
    private String joinTime;
    private String serviceArea;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
