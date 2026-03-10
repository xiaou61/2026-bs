package com.eldercare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("check_result")
public class CheckResult {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long appointmentId;
    private Long elderId;
    private Long itemId;
    private String itemValue;
    private Integer abnormalFlag;
    private String conclusion;
    private Long doctorId;
    private LocalDateTime checkTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
