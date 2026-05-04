package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("order_message")
public class OrderMessage {
    @TableId
    private Long id;
    private Long orderId;
    private Long senderId;
    private String senderType;
    private String messageContent;
    private Integer sensitiveFlag;
    private LocalDateTime sendTime;
    private LocalDateTime createTime;
}
