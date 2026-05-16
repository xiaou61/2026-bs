package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccessibleRoute {
    private Long id;
    private String routeNo;
    private String routeName;
    private String routeType;
    private String travelScenario;
    private Integer suggestedDuration;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
