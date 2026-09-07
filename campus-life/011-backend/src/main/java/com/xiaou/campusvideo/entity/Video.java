package com.xiaou.campusvideo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("video")
public class Video {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private String title;
    
    private String description;
    
    private String videoUrl;
    
    private String coverUrl;
    
    private Integer duration;
    
    private Integer width;
    
    private Integer height;
    
    private Long fileSize;
    
    private String location;
    
    private Integer permission;
    
    private Integer playCount;
    
    private Integer likeCount;
    
    private Integer commentCount;
    
    private Integer shareCount;
    
    private Integer collectCount;
    
    private BigDecimal heatScore;
    
    private Integer status;
    
    private String auditReason;
    
    private Integer isTop;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    private User user;
    
    @TableField(exist = false)
    private List<Topic> topics;
    
    @TableField(exist = false)
    private Boolean isLike;
    
    @TableField(exist = false)
    private Boolean isCollect;
}

