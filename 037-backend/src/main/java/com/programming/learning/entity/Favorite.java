package com.programming.learning.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 收藏实体
 */
@Data
public class Favorite {
    /**
     * 收藏ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 目标类型（COURSE/QUESTION/ARTICLE/CODE）
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
