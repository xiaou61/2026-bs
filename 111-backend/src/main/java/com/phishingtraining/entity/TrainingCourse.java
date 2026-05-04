package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("training_course")
public class TrainingCourse {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String courseName;
    private String courseCode;
    private String categoryName;
    private String teacherName;
    private Integer lessonMinutes;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
