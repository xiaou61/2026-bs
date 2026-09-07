package com.smartwarehouse.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InboundOrder {
    private Long id;
    private String inboundNo;
    private String supplierName;
    private String itemName;
    private String targetLocation;
    private String planTime;
    private Integer quantity;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
