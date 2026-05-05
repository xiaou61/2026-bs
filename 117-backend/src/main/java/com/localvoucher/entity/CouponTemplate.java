package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("coupon_template")
public class CouponTemplate {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String templateNo;
    private String couponName;
    private String merchantName;
    private String couponType;
    private BigDecimal faceValue;
    private BigDecimal thresholdAmount;
    private Integer validDays;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
