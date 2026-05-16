package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("course_catalog")
public class CourseCatalog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String courseNo;
    private String courseName;
    private String lecturerName;
    private Integer creditHours;
    private String deliveryMode;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



