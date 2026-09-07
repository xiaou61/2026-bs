package com.xiaou.campusclub.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private Long topicId;
    private Long parentId;
    private Long replyToId;
    private String content;
}

