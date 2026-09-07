package com.floodcity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("emergency_plan")
public class EmergencyPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String planNo;
    private String planName;
    private String applyLevel;
    private String departmentName;
    private String startCondition;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
