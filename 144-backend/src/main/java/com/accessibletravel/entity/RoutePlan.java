package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RoutePlan {
    private Long id;
    private String planNo;
    private String volunteerNo;
    private String routeType;
    private String routeDetail;
    private String generateTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
