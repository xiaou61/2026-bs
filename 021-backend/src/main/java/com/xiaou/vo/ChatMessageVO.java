package com.xiaou.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ChatMessageVO {

    private Long id;
    private Long productId;
    private Long senderId;
    private String senderName;
    private String senderAvatar;
    private Long receiverId;
    private String receiverName;
    private String receiverAvatar;
    private String content;
    private Integer isRead;
    private String messageType;
    private BigDecimal bargainPrice;
    private String bargainStatus;
    private LocalDateTime createTime;
}
