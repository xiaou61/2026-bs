package com.rental.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 租金账单实体
 */
@Data
@TableName("bill")
public class Bill {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String billNo;

    private Long contractId;

    private Long tenantId;

    private Long landlordId;

    private BigDecimal amount;

    private String billMonth;

    private LocalDate dueDate;

    private BigDecimal paidAmount;

    private LocalDateTime paidTime;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
