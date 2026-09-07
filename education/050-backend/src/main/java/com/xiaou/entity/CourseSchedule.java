package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("course_schedule")
public class CourseSchedule {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long courseId;
    private Integer weekDay; // 1-7
    private LocalTime startTime;
    private LocalTime endTime;
    private String classroom;
    private String building;
    private Integer startWeek;
    private Integer endWeek;
    private LocalDateTime createTime;
    
    @TableField(exist = false)
    private String courseName;
}
