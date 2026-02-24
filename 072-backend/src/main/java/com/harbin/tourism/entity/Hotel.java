package com.harbin.tourism.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("hotel")
public class Hotel {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String type;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private String description;
    private String coverImg;
    private String facilities;
    private BigDecimal rating;
    private Integer status;
    private LocalDateTime createdAt;
}
