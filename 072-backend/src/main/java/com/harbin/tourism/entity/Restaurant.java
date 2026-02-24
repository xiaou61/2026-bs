package com.harbin.tourism.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("restaurant")
public class Restaurant {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String cuisineType;
    private BigDecimal pricePerPerson;
    private String description;
    private String coverImg;
    private String recommendedDish;
    private String tags;
    private BigDecimal rating;
    private Integer status;
    private LocalDateTime createdAt;
}
