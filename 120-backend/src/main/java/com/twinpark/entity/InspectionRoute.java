package com.twinpark.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InspectionRoute {
    private Long id;
    private String routeNo;
    private String routeName;
    private String buildingName;
    private Integer pointCount;
    private Integer estimateMinutes;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
