package com.property.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FeeOrderVO {
    private Long id;
    private String orderNo;
    private Long houseId;
    private String houseName;
    private Long ownerId;
    private String ownerName;
    private BigDecimal amount;
    private String feeMonth;
    private Integer status;
    private LocalDateTime payTime;
    private String remark;
    private Long creatorId;
    private String creatorName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
