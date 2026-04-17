package com.xiaou.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChatSendDTO {

    @NotNull(message = "商品不能为空")
    private Long productId;

    @NotNull(message = "接收者不能为空")
    private Long receiverId;

    @NotBlank(message = "消息内容不能为空")
    @Size(max = 1000, message = "消息内容不能超过1000字符")
    private String content;
}
