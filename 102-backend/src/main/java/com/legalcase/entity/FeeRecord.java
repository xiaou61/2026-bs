package com.legalcase.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FeeRecord {
    private Long id;
    private Long caseId;
    private Long clientId;
    private String feeType;
    private BigDecimal amount;
    private Integer payStatus;
    private LocalDateTime payTime;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
