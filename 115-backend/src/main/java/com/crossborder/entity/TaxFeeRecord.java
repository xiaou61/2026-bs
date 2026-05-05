package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("tax_fee_record")
public class TaxFeeRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String feeNo;
    private String orderNo;
    private String taxType;
    private BigDecimal taxAmount;
    private String currencyCode;
    private String payStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
