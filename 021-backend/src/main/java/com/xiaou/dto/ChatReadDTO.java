package com.xiaou.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatReadDTO {

    @NotNull(message = "会话用户不能为空")
    private Long targetUserId;

    @NotNull(message = "商品不能为空")
    private Long productId;
}
