package com.xiaou.confession.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("post")
public class Post {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String postNo;
    private Long userId;
    private String anonymousNickname;
    private String anonymousAvatar;
    private String category;
    private String title;
    private String content;
    private String images;
    private String tags;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private BigDecimal hotScore;
    private Integer isTop;
    private Integer status;
    private String auditReason;
    private String sensitiveWords;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}

