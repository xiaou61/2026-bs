package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("quality_task")
public class QualityTask {
    @TableId
    private Long id;
    private String taskNo;
    private Long orderId;
    private Long qaUserId;
    private String taskName;
    private String priority;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime finishTime;
}
