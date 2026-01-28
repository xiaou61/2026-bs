package com.agriculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("production_task")
public class ProductionTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long planId;
    private String taskName;
    private String taskType;
    private Long assigneeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
    private LocalDateTime completionTime;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
