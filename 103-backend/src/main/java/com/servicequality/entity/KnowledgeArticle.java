package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("knowledge_article")
public class KnowledgeArticle {
    @TableId
    private Long id;
    private Long categoryId;
    private String title;
    private String keywords;
    private String content;
    private Integer status;
    private Integer hitCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
