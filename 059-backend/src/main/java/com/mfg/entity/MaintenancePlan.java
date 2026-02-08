package com.mfg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("maintenance_plan")
public class MaintenancePlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long equipmentId;
    private String planName;
    private Integer cycleDays;
    private LocalDate lastMaintainDate;
    private LocalDate nextMaintainDate;
    private String content;
    private String status;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String equipmentName;
}
