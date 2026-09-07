package com.property.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HouseVO {
    private Long id;
    private Long buildingId;
    private String buildingName;
    private String unitNo;
    private String roomNo;
    private BigDecimal area;
    private Long ownerId;
    private String ownerName;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
