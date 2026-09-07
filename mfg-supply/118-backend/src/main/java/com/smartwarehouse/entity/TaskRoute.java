package com.smartwarehouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TaskRoute {
    private Long id;
    private String routeNo;
    private String taskNo;
    private String startPoint;
    private String endPoint;
    private BigDecimal distanceMeter;
    private Integer estimateSeconds;
    private String congestionLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
