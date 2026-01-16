package com.psychology.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;
    private String cover;
    private String category;
    private String content;
    private String tags;
    private Long authorId;
    private Integer viewCount;
    private Integer likeCount;
    private Integer collectCount;
    private String status;
    private Boolean isTop;
    private Boolean isRecommended;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
