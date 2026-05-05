package com.twinpark.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ParkBuilding {
    private Long id;
    private String buildingNo;
    private String buildingName;
    private Integer floorCount;
    private BigDecimal areaSize;
    private String managerName;
    private String twinStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
