package com.agriculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("material_record")
public class MaterialRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long materialId;
    private Integer type;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String purpose;
    private Long operatorId;
    private LocalDate recordDate;
    private String remark;
    private LocalDateTime createTime;
}
