package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("handling_task")
public class HandlingTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskNo;
    private String complaintTitle;
    private String assigneeName;
    private String assignTime;
    private String deadlineTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






