package com.xiaou.campusvideo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_points_log")
public class UserPointsLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private String changeType;
    
    private Integer changePoints;
    
    private Integer beforePoints;
    
    private Integer afterPoints;
    
    private String reason;
    
    private Long relatedId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

