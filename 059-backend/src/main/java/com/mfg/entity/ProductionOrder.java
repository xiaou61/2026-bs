package com.mfg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("production_order")
public class ProductionOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long productId;
    private Long equipmentId;
    private Integer planQuantity;
    private Integer actualQuantity;
    private String status;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String productName;
    @TableField(exist = false)
    private String equipmentName;
}
