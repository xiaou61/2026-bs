package com.smartwarehouse.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OutboundOrder {
    private Long id;
    private String outboundNo;
    private String customerName;
    private String itemName;
    private String pickLocation;
    private String priorityLevel;
    private Integer quantity;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
