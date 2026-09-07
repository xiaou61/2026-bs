package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class UserVehicle {
    private Long id;
    private String vehicleNo;
    private String ownerName;
    private String plateNo;
    private BigDecimal batteryCapacity;
    private String vehicleModel;
    private String phone;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
