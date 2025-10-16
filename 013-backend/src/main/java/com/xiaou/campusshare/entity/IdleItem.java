package com.xiaou.campusshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("idle_item")
public class IdleItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String category;
    private String title;
    private String description;
    private String images;
    private Integer conditionLevel;
    private BigDecimal originalPrice;
    private BigDecimal hourlyPrice;
    private BigDecimal dailyPrice;
    private BigDecimal weeklyPrice;
    private BigDecimal depositAmount;
    private String availableTime;
    private Integer pickupMethod;
    private String pickupAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String contactPhone;
    private Integer viewCount;
    private Integer orderCount;
    private BigDecimal rating;
    private Integer status;
    private String auditReason;
    private Integer isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}

