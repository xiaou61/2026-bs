package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("masking_task")
public class MaskingTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskNo;
    private String datasetName;
    private String strategyName;
    private String executeMode;
    private String ownerName;
    private LocalDateTime startedTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
