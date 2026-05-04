package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("recognition_task")
public class RecognitionTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskNo;
    private String datasetName;
    private String ruleScope;
    private String scanMode;
    private String ownerName;
    private LocalDateTime startedTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
