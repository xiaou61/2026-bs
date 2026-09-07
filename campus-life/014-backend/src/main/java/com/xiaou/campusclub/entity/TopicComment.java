package com.xiaou.campusclub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("topic_comment")
public class TopicComment {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long topicId;
    
    private Long userId;
    
    private Long parentId;
    
    private Long replyToId;
    
    private String content;
    
    private Integer likeCount;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

