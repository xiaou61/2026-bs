package com.twinpark.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InspectionPoint {
    private Long id;
    private String pointNo;
    private String pointName;
    private String deviceName;
    private String floorName;
    private String checkStandard;
    private String lastResult;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
