package com.xiaou.campusvideo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("video_report")
public class VideoReport {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long videoId;
    
    private String reportType;
    
    private String reportReason;
    
    private Integer status;
    
    private String handleResult;
    
    private Long handleAdminId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime handleTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

