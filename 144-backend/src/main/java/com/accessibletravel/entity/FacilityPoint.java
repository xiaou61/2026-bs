package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FacilityPoint {
    private Long id;
    private String pointNo;
    private String pointName;
    private String facilityType;
    private String addressDetail;
    private String openStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
