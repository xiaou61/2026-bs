package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ColdDevice {
    private Long id;
    private String deviceName;
    private String deviceNo;
    private String vehicleNo;
    private String ownerName;
    private String onlineStatus;
    private String lastHeartbeat;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
