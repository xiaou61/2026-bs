package com.bike.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class WalletRecord {
    private Long id;
    private Long userId;
    private Integer type;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private String description;
    private Date createTime;
}
