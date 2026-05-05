package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("settlement_bill")
public class SettlementBill {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String billNo;
    private String merchantName;
    private String currencyCode;
    private BigDecimal originalAmount;
    private BigDecimal cnyAmount;
    private String settlementStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
