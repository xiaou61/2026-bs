package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FacilityPoint {
    private Long id;
    private String categoryNo;
    private String categoryName;
    private String usageScope;
    private String controlMode;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}





