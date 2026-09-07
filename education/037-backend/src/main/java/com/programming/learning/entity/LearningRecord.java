package com.programming.learning.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学习记录实体
 */
@Data
public class LearningRecord {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 章节ID
     */
    private Long chapterId;

    /**
     * 学习进度（0-100）
     */
    private Integer progress;

    /**
     * 学习时长（分钟）
     */
    private Integer duration;

    /**
     * 是否完成
     */
    private Boolean isCompleted;

    /**
     * 完成时间
     */
    private LocalDateTime completedTime;

    /**
     * 最后学习时间
     */
    private LocalDateTime lastLearnTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
