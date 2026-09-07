package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("verification_record")
public class VerificationRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String verifyNo;
    private String couponNo;
    private String storeName;
    private String cashierName;
    private BigDecimal consumeAmount;
    private BigDecimal discountAmount;
    private String verifyTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
