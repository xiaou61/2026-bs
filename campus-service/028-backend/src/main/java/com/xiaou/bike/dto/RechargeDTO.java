package com.xiaou.bike.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 充值DTO
 */
@Data
public class RechargeDTO {

    /**
     * 充值金额
     */
    @NotNull(message = "充值金额不能为空")
    @DecimalMin(value = "1", message = "充值金额不能小于1元")
    private BigDecimal amount;

    /**
     * 类型：1余额充值 2押金缴纳
     */
    @NotNull(message = "充值类型不能为空")
    private Integer type;

    /**
     * 支付方式：1微信 2支付宝 3银行卡
     */
    private Integer payMethod = 1;
}
