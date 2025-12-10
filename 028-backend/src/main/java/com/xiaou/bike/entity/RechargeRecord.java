package com.xiaou.bike.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 充值记录实体类
 */
@Data
@TableName("recharge_record")
public class RechargeRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 充值金额
     */
    private BigDecimal amount;

    /**
     * 类型：1余额充值 2押金缴纳
     */
    private Integer type;

    /**
     * 支付方式：1微信 2支付宝 3银行卡
     */
    private Integer payMethod;

    /**
     * 状态：0待支付 1已完成 2已取消
     */
    private Integer status;

    /**
     * 交易流水号
     */
    private String transactionNo;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
