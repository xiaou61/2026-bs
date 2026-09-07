package com.teachres.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EvalRecordItem {
    private Long id;
    private Long recordId;
    private Long indicatorId;
    private BigDecimal score;
    private LocalDateTime createTime;
}
