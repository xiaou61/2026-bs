package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("attendance_record")
public class AttendanceRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long taskId;
    private Long studentId;
    private Long courseId;
    private LocalDateTime signTime;
    private Integer signType;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
    private BigDecimal distance;
    private String deviceInfo;
    private String ipAddress;
    private Integer status; // 0-未签 1-已签 2-迟到 3-早退 4-请假 5-补签
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    private String studentName;
    @TableField(exist = false)
    private String studentNo;
}
