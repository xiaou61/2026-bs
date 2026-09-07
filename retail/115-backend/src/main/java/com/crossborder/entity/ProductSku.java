package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("product_sku")
public class ProductSku {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String skuNo;
    private String productName;
    private String categoryName;
    private String hsCode;
    private String originCountry;
    private BigDecimal declaredValue;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
