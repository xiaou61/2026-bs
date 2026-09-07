package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProtectiveSupply {
    private Long id;
    private String supplyNo;
    private String supplyName;
    private String supplyType;
    private Integer stockQty;
    private Integer safeQty;
    private String warehouseName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
