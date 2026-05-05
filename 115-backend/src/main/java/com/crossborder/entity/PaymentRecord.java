package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("payment_record")
public class PaymentRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String paymentNo;
    private String billNo;
    private String payChannel;
    private BigDecimal payAmount;
    private String currencyCode;
    private String payTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
