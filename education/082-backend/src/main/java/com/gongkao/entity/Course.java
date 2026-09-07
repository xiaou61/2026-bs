package com.gongkao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("course")
public class Course {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long subjectId;
    private Long teacherId;
    private String title;
    private String level;
    private String cover;
    private String summary;
    private Integer studyHours;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

