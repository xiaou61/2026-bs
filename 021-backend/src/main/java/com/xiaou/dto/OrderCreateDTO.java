package com.xiaou.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCreateDTO {

    @NotNull(message = "商品不能为空")
    private Long productId;
}
