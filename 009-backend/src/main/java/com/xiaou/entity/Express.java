package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("express")
public class Express {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String trackingNumber;
    private String expressCompany;
    private String pickupCode;
    private String recipientName;
    private String recipientPhone;
    private Long recipientId;
    private Long stationId;
    private String shelfLocation;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime outTime;
    private Long inOperatorId;
    private String inOperatorName;
    private Long outOperatorId;
    private String outOperatorName;
    private Integer overdueDays;
    private BigDecimal overdueFee;
    private Integer isNotified;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}

