package com.xiaou.confession.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long postId;
    private Long userId;
    private String anonymousNickname;
    private String anonymousAvatar;
    private Long parentId;
    private String replyToNickname;
    private String content;
    private Integer likeCount;
    private Integer floorNumber;
    private Integer isAuthor;
    private Integer status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

