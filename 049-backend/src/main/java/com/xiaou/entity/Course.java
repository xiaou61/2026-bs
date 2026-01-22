package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("course")
public class Course {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long subjectId;
    private Long teacherId;
    private String title;
    private String cover;
    private String description;
    private BigDecimal price;
    private Integer isFree;
    private Integer chapterCount;
    private Integer studentCount;
    private Integer viewCount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    private String subjectName;
    @TableField(exist = false)
    private String teacherName;
}
