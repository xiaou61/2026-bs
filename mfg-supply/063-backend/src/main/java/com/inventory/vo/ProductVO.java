package com.inventory.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductVO {
    private Long id;
    private String productNo;
    private String name;
    private Long categoryId;
    private String categoryName;
    private String spec;
    private String unit;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer stock;
    private Integer stockWarn;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
