package com.smartwarehouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ChargingStation {
    private Long id;
    private String stationNo;
    private String stationName;
    private String zoneName;
    private Integer portCount;
    private Integer idlePortCount;
    private BigDecimal chargePower;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
