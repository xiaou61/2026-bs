package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("attendance_task")
public class AttendanceTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long courseId;
    private Long scheduleId;
    private Long teacherId;
    private String title;
    private Integer signType; // 1-普通 2-定位 3-二维码 4-数字
    private String signCode;
    private String qrCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
    private Integer distance;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;
    private LocalDateTime lateTime;
    private Integer status; // 0-已结束 1-进行中 2-未开始
    private Integer totalCount;
    private Integer signedCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    private String courseName;
    @TableField(exist = false)
    private String teacherName;
}
