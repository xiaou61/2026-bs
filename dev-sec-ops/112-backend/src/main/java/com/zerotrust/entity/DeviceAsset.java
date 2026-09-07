package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeviceAsset {
    private Long id;
    private String deviceName;
    private String deviceNo;
    private String deviceType;
    private String osVersion;
    private String ownerName;
    private String departmentName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
