package com.teacher.new.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("teaching_class")
public class TeachingClass {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String gradeName;
    private String className;
    private String headTeacher;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
