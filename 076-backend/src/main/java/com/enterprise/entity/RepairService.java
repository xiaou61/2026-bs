package com.enterprise.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("enterprise_info")
public class RepairService {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long categoryId;
    private Long sellerId;
    private String title;
    private String serviceName;
    private String storeName;
    private String vehicleType;
    private BigDecimal price;
    private Integer stock;
    private String cover;
    private String description;
    private String bookingType;
    private Integer viewCount;
    private Integer bookedCount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}




