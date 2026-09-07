package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("payment_record")
public class PaymentRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String paymentNo;
    private String recordNo;
    private String ownerName;
    private BigDecimal payAmount;
    private String payMethod;
    private String payTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
