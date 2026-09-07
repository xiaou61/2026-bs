package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class ElectricDevice {
    private Long id;
    private String deviceNo;
    private String homeNo;
    private String deviceName;
    private String deviceType;
    private BigDecimal ratedPower;
    private String roomName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
