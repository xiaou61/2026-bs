package com.repair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("repair_order")
public class RepairOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private String contactName;
    private String contactPhone;
    private String province;
    private String city;
    private String district;
    private String address;
    private Long categoryId;
    private String brand;
    private String model;
    private String faultDesc;
    private String images;
    private LocalDateTime expectTime;
    private Integer urgency;
    private Integer status;
    private Long technicianId;
    private LocalDateTime visitTime;
    private LocalDateTime finishTime;
    private BigDecimal totalFee;
    private BigDecimal partsFee;
    private BigDecimal laborFee;
    private Integer payStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
