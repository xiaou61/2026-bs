package com.xiaou.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BargainDTO {

    @NotNull(message = "商品不能为空")
    private Long productId;

    @NotNull(message = "接收者不能为空")
    private Long receiverId;

    @NotNull(message = "议价金额不能为空")
    @DecimalMin(value = "0.01", message = "议价金额必须大于0")
    @Digits(integer = 8, fraction = 2, message = "议价金额格式不正确")
    private BigDecimal bargainPrice;

    @Size(max = 1000, message = "议价说明不能超过1000字符")
    private String content;
}
