package com.teacher.new.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("subject_info")
public class SubjectInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String subjectName;
    private String subjectCode;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
