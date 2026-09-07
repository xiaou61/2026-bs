package com.programming.learning.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 章节实体
 */
@Data
public class Chapter {
    /**
     * 章节ID
     */
    private Long id;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 章节标题
     */
    private String title;

    /**
     * 章节内容（Markdown格式）
     */
    private String content;

    /**
     * 章节时长（分钟）
     */
    private Integer duration;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 是否免费
     */
    private Boolean isFree;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
