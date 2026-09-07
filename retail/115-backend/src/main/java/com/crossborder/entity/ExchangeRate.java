package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("exchange_rate")
public class ExchangeRate {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String rateNo;
    private String baseCurrency;
    private String targetCurrency;
    private BigDecimal rateValue;
    private String effectiveDate;
    private String providerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
