package com.teachres.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EvalIndicator {
    private Long id;
    private String indicatorName;
    private BigDecimal weight;
    private Integer sort;
    private Integer status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
