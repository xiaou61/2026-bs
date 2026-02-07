package com.milk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("milk_product")
public class MilkProduct {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long categoryId;
    private String name;
    private String image;
    private BigDecimal price;
    private String unit;
    private String spec;
    private String description;
    private Integer stock;
    private Integer status;
    private LocalDateTime createTime;
}
