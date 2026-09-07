package com.xiaou.campusshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("order_info")
public class OrderInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private String orderType;
    private Long userId;
    private Long providerId;
    private Long itemId;
    private String itemTitle;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private Integer plannedDuration;
    private Integer actualDuration;
    private BigDecimal rentalFee;
    private BigDecimal depositAmount;
    private BigDecimal platformFee;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private Integer depositStatus;
    private Integer paymentStatus;
    private Integer orderStatus;
    private String cancelReason;
    private Integer isOvertime;
    private BigDecimal overtimeFee;
    private Integer isDamaged;
    private BigDecimal damageFee;
    private String pickupAddress;
    private String returnAddress;
    private String requirement;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}

