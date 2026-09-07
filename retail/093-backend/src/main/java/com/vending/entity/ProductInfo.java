package com.vending.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("product_info")
public class ProductInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String productNo;
    private String name;
    private Long categoryId;
    private String brand;
    private String spec;
    private String cover;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer stockWarn;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String categoryName;
}
