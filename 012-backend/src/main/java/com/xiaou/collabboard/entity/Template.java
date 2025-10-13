package com.xiaou.collabboard.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("template")
public class Template {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    private Long teamId;
    private String templateName;
    private String templateType;
    private String category;
    private String coverUrl;
    private String description;
    private String content;
    private Integer useCount;
    private Integer collectCount;
    private BigDecimal rating;
    private Integer isPublic;
    private Integer isOfficial;
    private Integer status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}

