package com.programming.learning.entity;

import com.programming.learning.enums.QuestionStatus;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 问题实体
 */
@Data
public class Question {
    /**
     * 问题ID
     */
    private Long id;

    /**
     * 提问用户ID
     */
    private Long userId;

    /**
     * 提问用户昵称
     */
    private String nickname;

    /**
     * 提问用户头像
     */
    private String avatar;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 问题内容（Markdown格式）
     */
    private String content;

    /**
     * 问题标签，逗号分隔
     */
    private String tags;

    /**
     * 悬赏积分
     */
    private Integer bountyScore;

    /**
     * 问题状态
     */
    private QuestionStatus status;

    /**
     * 采纳的答案ID
     */
    private Long acceptedAnswerId;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 回答数
     */
    private Integer answerCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 收藏数
     */
    private Integer favoriteCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
