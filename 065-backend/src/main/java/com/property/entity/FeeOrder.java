package com.property.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FeeOrder {
    private Long id;
    private String orderNo;
    private Long houseId;
    private Long ownerId;
    private BigDecimal amount;
    private String feeMonth;
    private Integer status;
    private LocalDateTime payTime;
    private String remark;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
