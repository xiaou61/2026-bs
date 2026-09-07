package com.programming.learning.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 回答实体
 */
@Data
public class Answer {
    /**
     * 回答ID
     */
    private Long id;

    /**
     * 问题ID
     */
    private Long questionId;

    /**
     * 回答用户ID
     */
    private Long userId;

    /**
     * 回答用户昵称
     */
    private String nickname;

    /**
     * 回答用户头像
     */
    private String avatar;

    /**
     * 回答内容（Markdown格式）
     */
    private String content;

    /**
     * 是否被采纳
     */
    private Boolean isAccepted;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
