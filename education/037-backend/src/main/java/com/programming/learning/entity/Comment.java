package com.programming.learning.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论实体
 */
@Data
public class Comment {
    /**
     * 评论ID
     */
    private Long id;

    /**
     * 评论用户ID
     */
    private Long userId;

    /**
     * 评论用户昵称
     */
    private String nickname;

    /**
     * 评论用户头像
     */
    private String avatar;

    /**
     * 目标类型（QUESTION/ANSWER/ARTICLE/CODE）
     */
    private String targetType;

    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 父评论ID（0表示顶级评论）
     */
    private Long parentId;

    /**
     * 回复的评论ID（0表示不是回复）
     */
    private Long replyToId;

    /**
     * 回复的用户昵称
     */
    private String replyToNickname;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
