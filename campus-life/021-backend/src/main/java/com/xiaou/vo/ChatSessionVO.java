package com.xiaou.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatSessionVO {

    private Long productId;
    private String productTitle;
    private String productImages;
    private Long targetUserId;
    private String targetUserName;
    private String targetUserAvatar;
    private String lastMessage;
    private String lastMessageType;
    private BigDecimal bargainPrice;
    private String bargainStatus;
    private LocalDateTime lastTime;
    private Integer unreadCount;

    public List<String> getImageList() {
        if (productImages == null || productImages.isEmpty()) {
            return List.of();
        }
        return List.of(productImages.split(","));
    }
}
