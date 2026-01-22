package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("message")
public class Message {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Integer type; // 1-签到提醒 2-审批通知 3-系统消息
    private Long relatedId;
    private Integer isRead;
    private LocalDateTime createTime;
}
