package com.xiaou.bike.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 消费记录实体类
 */
@Data
@TableName("consumption_record")
public class ConsumptionRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 关联订单ID
     */
    private Long orderId;

    /**
     * 消费金额
     */
    private BigDecimal amount;

    /**
     * 类型：1骑行消费 2退款
     */
    private Integer type;

    /**
     * 消费后余额
     */
    private BigDecimal balanceAfter;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
