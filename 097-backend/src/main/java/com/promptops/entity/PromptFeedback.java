package com.promptops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("prompt_feedback")
public class PromptFeedback {
    private Long id;
    private Long assetId;
    private Long userId;
    private String content;
    private String priority;
    private Integer status;
    private String replyContent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String assetTitle;
    @TableField(exist = false)
    private String username;
}
