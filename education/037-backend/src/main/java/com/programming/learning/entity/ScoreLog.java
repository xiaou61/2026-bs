package com.programming.learning.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 积分记录实体
 */
@Data
public class ScoreLog {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 积分变化（正数为增加，负数为减少）
     */
    private Integer scoreChange;

    /**
     * 变化后积分
     */
    private Integer afterScore;

    /**
     * 积分来源类型
     */
    private String sourceType;

    /**
     * 来源ID
     */
    private Long sourceId;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
