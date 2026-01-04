package com.programming.learning.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 代码片段实体
 */
@Data
public class CodeSnippet {
    /**
     * 代码片段ID
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
     * 代码标题
     */
    private String title;

    /**
     * 代码描述
     */
    private String description;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 代码内容
     */
    private String code;

    /**
     * 代码标签，逗号分隔
     */
    private String tags;

    /**
     * Fork来源ID
     */
    private Long forkFromId;

    /**
     * Fork次数
     */
    private Integer forkCount;

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
