package com.agriculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("agricultural_material")
public class AgriculturalMaterial {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String category;
    private String specification;
    private String unit;
    private Integer stock;
    private Integer warningStock;
    private BigDecimal unitPrice;
    private String supplier;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
