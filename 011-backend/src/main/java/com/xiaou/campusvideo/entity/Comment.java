package com.xiaou.campusvideo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long videoId;
    
    private Long userId;
    
    private Long parentId;
    
    private Long replyToUserId;
    
    private String content;
    
    private Integer likeCount;
    
    private Integer replyCount;
    
    private Integer status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    private User user;
    
    @TableField(exist = false)
    private User replyToUser;
    
    @TableField(exist = false)
    private List<Comment> replies;
    
    @TableField(exist = false)
    private Boolean isLike;
}

