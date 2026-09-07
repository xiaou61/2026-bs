package com.xiaou.campusshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("shared_item")
public class SharedItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String itemNo;
    private String itemType;
    private String itemModel;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String locationName;
    private Integer batteryLevel;
    private Integer status;
    private BigDecimal hourlyPrice;
    private BigDecimal dailyMaxPrice;
    private BigDecimal depositAmount;
    private Integer totalUsageCount;
    private BigDecimal totalDistance;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastMaintainTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}

