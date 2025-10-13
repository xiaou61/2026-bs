package com.xiaou.collabboard.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("document")
public class Document {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    private Long teamId;
    private Long folderId;
    private String title;
    private String docType;
    private String content;
    private String coverUrl;
    private String description;
    private String tags;
    private Integer viewCount;
    private Integer editCount;
    private Integer collabCount;
    private Integer isPublic;
    private Integer isTemplate;
    private Integer isStarred;
    private String shareLink;
    private String sharePassword;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shareExpireTime;
    
    private Integer status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleteTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}

