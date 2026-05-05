package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DeviceProfile {
    private Long id;
    private String deviceNo;
    private String deviceName;
    private String modelName;
    private String deptName;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
