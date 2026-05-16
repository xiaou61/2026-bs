package com.psychologycare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("duty_schedule")
public class DutySchedule {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String scheduleNo;
    private String dutyTeacher;
    private String dutyDate;
    private String dutyPeriod;
    private String effectiveTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







