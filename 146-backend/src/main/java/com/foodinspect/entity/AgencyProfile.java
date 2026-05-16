package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AgencyProfile {
    private Long id;
    private String agencyNo;
    private String agencyName;
    private String qualificationLevel;
    private String entryDate;
    private String specialtyArea;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






