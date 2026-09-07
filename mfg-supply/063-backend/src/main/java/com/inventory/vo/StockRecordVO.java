package com.inventory.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockRecordVO {
    private Long id;
    private String bizType;
    private String bizNo;
    private Long productId;
    private String productName;
    private Integer changeQty;
    private Integer beforeStock;
    private Integer afterStock;
    private String remark;
    private Long operatorId;
    private String operatorName;
    private LocalDateTime createTime;
}
