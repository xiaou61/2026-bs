package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VehicleProfile {
    private Long id;
    private String vehicleNo;
    private String plateNumber;
    private String vinCode;
    private String vehicleModel;
    private String usageType;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
