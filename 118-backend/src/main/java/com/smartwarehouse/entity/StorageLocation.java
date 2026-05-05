package com.smartwarehouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StorageLocation {
    private Long id;
    private String locationNo;
    private String zoneName;
    private String locationType;
    private BigDecimal maxWeight;
    private BigDecimal volumeCapacity;
    private String heatLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
