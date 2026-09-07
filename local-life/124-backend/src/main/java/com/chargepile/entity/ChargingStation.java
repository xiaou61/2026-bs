package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChargingStation {
    private Long id;
    private String stationNo;
    private String stationName;
    private String cityName;
    private String addressName;
    private String operatorName;
    private String openTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
