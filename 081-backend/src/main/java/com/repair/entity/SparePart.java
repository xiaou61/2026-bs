package com.repair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("spare_part")
public class SparePart {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String partCode;
    private String brand;
    private String specification;
    private Integer stock;
    private BigDecimal unitPrice;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
