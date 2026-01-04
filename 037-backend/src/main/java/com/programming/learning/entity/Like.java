package com.programming.learning.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 点赞实体
 */
@Data
public class Like {
    /**
     * 点赞ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 目标类型（COURSE/QUESTION/ANSWER/ARTICLE/CODE）
     */
    private String targetType;

    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
