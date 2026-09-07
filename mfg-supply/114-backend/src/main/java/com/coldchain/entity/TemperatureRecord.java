package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TemperatureRecord {
    private Long id;
    private String recordNo;
    private String orderNo;
    private String deviceNo;
    private String cargoName;
    private Integer temperatureValue;
    private Integer humidityValue;
    private String collectTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
