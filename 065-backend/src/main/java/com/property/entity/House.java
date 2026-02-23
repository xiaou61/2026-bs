package com.property.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class House {
    private Long id;
    private Long buildingId;
    private String unitNo;
    private String roomNo;
    private BigDecimal area;
    private Long ownerId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
