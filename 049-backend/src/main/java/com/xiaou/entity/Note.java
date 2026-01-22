package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("note")
public class Note {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long subjectId;
    private Long courseId;
    private Long questionId;
    private String title;
    private String content;
    private Integer isPublic;
    private Integer likeCount;
    private Integer viewCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    private String subjectName;
    @TableField(exist = false)
    private String authorName;
}
