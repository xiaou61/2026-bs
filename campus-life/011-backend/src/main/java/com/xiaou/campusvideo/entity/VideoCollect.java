package com.xiaou.campusvideo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("video_collect")
public class VideoCollect {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long videoId;
    
    private Long folderId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

