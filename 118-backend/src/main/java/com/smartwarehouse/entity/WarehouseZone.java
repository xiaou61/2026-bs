package com.smartwarehouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WarehouseZone {
    private Long id;
    private String zoneNo;
    private String zoneName;
    private String temperatureType;
    private Integer aisleCount;
    private String managerName;
    private BigDecimal capacityRate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
