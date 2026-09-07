package com.droneinspect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("inspection_task")
public class InspectionTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskNo;
    private String routeName;
    private String pilotName;
    private String planTime;
    private String priorityLevel;
    private Integer photoRequired;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
