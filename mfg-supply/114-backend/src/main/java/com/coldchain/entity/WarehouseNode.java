package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WarehouseNode {
    private Long id;
    private String nodeName;
    private String nodeCode;
    private String nodeType;
    private String regionName;
    private String managerName;
    private Integer capacityTon;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
