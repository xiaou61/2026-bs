package com.psychology.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("chat_message")
public class ChatMessage {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long appointmentId;
    private Long senderId;
    private String senderRole;
    private String content;
    private String messageType;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
