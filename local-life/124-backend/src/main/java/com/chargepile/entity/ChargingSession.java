package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class ChargingSession {
    private Long id;
    private String sessionNo;
    private String appointmentNo;
    private String pileNo;
    private String startTime;
    private BigDecimal energyKwh;
    private BigDecimal chargeAmount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
