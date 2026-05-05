package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class QrTrace {
    private Long id;
    private String traceNo;
    private String qrCode;
    private String deviceNo;
    private String currentLocation;
    private String flowStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
