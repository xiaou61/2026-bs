package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransportOrder {
    private Long id;
    private String orderNo;
    private String cargoName;
    private String fromNode;
    private String toNode;
    private String carrierName;
    private String vehicleNo;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
