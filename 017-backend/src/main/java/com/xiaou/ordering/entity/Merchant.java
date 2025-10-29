package com.xiaou.ordering.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("merchant")
public class Merchant {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String merchantName;
    private String licenseNo;
    private String contactPerson;
    private String contactPhone;
    private String password;
    private String address;
    private String location;
    private String category;
    private String image;
    private String description;
    private String notice;
    private String businessHours;
    private BigDecimal deliveryFee;
    private BigDecimal minPrice;
    private Integer avgPrepareTime;
    private BigDecimal rating;
    private Integer totalSales;
    private Integer monthSales;
    private Integer status;
    private Integer isBusy;
    private Integer auditStatus;
    private String auditRemark;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}

