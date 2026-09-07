package com.aigccopyright.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("audit_task")
public class AuditTask {
    private Long id;
    private String taskNo;
    private Long assetId;
    private String taskName;
    private String priority;
    private Integer status;
    private Long auditorId;
    private LocalDateTime createTime;
    private LocalDateTime finishTime;
}
