package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("attendance_stat")
public class AttendanceStat {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long courseId;
    private Long semesterId;
    private Integer totalCount;
    private Integer normalCount;
    private Integer lateCount;
    private Integer absentCount;
    private Integer leaveCount;
    private Integer makeupCount;
    private BigDecimal attendanceRate;
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    private String studentName;
    @TableField(exist = false)
    private String studentNo;
    @TableField(exist = false)
    private String courseName;
}
