package com.inventory.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PurchaseOrder {
    private Long id;
    private String orderNo;
    private Long supplierId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal amount;
    private Integer status;
    private String remark;
    private Long creatorId;
    private LocalDateTime auditTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
