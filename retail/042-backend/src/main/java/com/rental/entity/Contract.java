package com.rental.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 租赁合同实体
 */
@Data
@TableName("contract")
public class Contract {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String contractNo;

    private Long houseId;

    private Long landlordId;

    private Long tenantId;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal monthlyRent;

    private BigDecimal deposit;

    private Integer paymentDay;

    private String paymentMethod;

    private String terms;

    private Integer status;

    private LocalDateTime signTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
