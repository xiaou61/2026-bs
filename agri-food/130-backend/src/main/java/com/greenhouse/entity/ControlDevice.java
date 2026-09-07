package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ControlDevice {
    private Long id;
    private String deviceNo;
    private String greenhouseNo;
    private String deviceName;
    private String deviceType;
    private String controlMode;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
