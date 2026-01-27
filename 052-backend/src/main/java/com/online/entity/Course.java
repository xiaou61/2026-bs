package com.online.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("course")
public class Course {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String cover;
    private String description;
    private Long categoryId;
    private Long teacherId;
    private BigDecimal price;
    private Integer isFree;
    private Integer learnCount;
    private Integer chapterCount;
    private Integer duration;
    private BigDecimal score;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String categoryName;
    @TableField(exist = false)
    private String teacherName;
}
