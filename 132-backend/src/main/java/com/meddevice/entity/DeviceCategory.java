package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DeviceCategory {
    private Long id;
    private String categoryNo;
    private String categoryName;
    private String riskLevel;
    private String sterilizeMethod;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
