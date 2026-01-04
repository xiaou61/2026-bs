package com.programming.learning.entity;

import com.programming.learning.enums.ArticleStatus;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文章实体
 */
@Data
public class Article {
    /**
     * 文章ID
     */
    private Long id;

    /**
     * 作者ID
     */
    private Long userId;

    /**
     * 作者昵称
     */
    private String nickname;

    /**
     * 作者头像
     */
    private String avatar;

    /**
     * 专栏ID
     */
    private Long columnId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章封面
     */
    private String cover;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 文章内容（Markdown格式）
     */
    private String content;

    /**
     * 文章标签，逗号分隔
     */
    private String tags;

    /**
     * 文章状态
     */
    private ArticleStatus status;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 收藏数
     */
    private Integer favoriteCount;

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
