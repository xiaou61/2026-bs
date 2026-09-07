package com.floodcity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("dispatch_task")
public class DispatchTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskNo;
    private String warningNo;
    private String taskType;
    private String responsibleUnit;
    private String deadlineTime;
    private String dispatcherName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
