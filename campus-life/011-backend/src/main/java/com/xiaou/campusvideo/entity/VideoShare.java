package com.xiaou.campusvideo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("video_share")
public class VideoShare {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long videoId;
    
    private Long originalUserId;
    
    private String shareText;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

