package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("course_enrollment")
public class CourseEnrollment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String enrollmentNo;
    private String courseName;
    private String learnerName;
    private String applyTime;
    private String approverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



