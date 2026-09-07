package com.xiaou.campusvideo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("topic")
public class Topic {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String topicName;
    
    private String description;
    
    private String coverUrl;
    
    private String category;
    
    private Integer videoCount;
    
    private Long viewCount;
    
    private Integer participantCount;
    
    private BigDecimal heatScore;
    
    private Integer status;
    
    private Integer isHot;
    
    private Integer sortOrder;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}

