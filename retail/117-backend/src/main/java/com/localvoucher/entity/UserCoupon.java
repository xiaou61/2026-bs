package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user_coupon")
public class UserCoupon {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String couponNo;
    private String consumerName;
    private String couponName;
    private String receiveTime;
    private String expireTime;
    private String sourceChannel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
