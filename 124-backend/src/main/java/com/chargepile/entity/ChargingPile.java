package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class ChargingPile {
    private Long id;
    private String pileNo;
    private String stationName;
    private BigDecimal powerKw;
    private String connectorType;
    private BigDecimal pricePerKwh;
    private String positionName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
