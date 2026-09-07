package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("payment_transfer")
public class PaymentTransfer {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String transferNo;
    private String settlementNo;
    private String merchantName;
    private BigDecimal transferAmount;
    private String bankAccount;
    private String transferTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
