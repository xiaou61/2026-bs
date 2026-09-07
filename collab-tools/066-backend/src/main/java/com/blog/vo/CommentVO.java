package com.blog.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentVO {
    private Long id;
    private Long postId;
    private String postTitle;
    private Long userId;
    private String userName;
    private String content;
    private Integer status;
    private String replyContent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
