package com.mfg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("material_stock_record")
public class MaterialStockRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long materialId;
    private String type;
    private BigDecimal quantity;
    private String reason;
    private String operator;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String materialName;
}
