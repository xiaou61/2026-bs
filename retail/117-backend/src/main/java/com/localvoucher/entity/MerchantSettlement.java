package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("merchant_settlement")
public class MerchantSettlement {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String settlementNo;
    private String merchantName;
    private String settlementCycle;
    private BigDecimal verifyAmount;
    private BigDecimal commissionAmount;
    private BigDecimal payableAmount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
