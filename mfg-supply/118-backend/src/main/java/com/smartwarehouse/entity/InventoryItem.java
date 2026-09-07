package com.smartwarehouse.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InventoryItem {
    private Long id;
    private String skuNo;
    private String itemName;
    private String batchNo;
    private String locationNo;
    private Integer quantity;
    private String turnoverLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
