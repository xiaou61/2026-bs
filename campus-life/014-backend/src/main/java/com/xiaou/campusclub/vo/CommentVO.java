package com.xiaou.campusclub.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentVO {
    private Long id;
    private Long topicId;
    private Long userId;
    private String username;
    private String avatar;
    private Long parentId;
    private Long replyToId;
    private String replyToUsername;
    private String content;
    private Integer likeCount;
    private Boolean isLiked;
    private List<CommentVO> replies;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

