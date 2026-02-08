package com.mfg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("maintenance_record")
public class MaintenanceRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long planId;
    private Long equipmentId;
    private String maintainType;
    private String content;
    private String maintainer;
    private BigDecimal cost;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String equipmentName;
    @TableField(exist = false)
    private String planName;
}
