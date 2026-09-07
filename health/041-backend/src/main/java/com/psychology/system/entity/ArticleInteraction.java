package com.psychology.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("article_interaction")
public class ArticleInteraction {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long articleId;
    private Long userId;
    private String type;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
