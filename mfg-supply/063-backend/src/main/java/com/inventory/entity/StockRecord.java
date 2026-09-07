package com.inventory.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockRecord {
    private Long id;
    private String bizType;
    private String bizNo;
    private Long productId;
    private Integer changeQty;
    private Integer beforeStock;
    private Integer afterStock;
    private String remark;
    private Long operatorId;
    private LocalDateTime createTime;
}
