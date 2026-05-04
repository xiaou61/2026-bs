package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("recall_event")
public class RecallEvent {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String recallNo;
    private String batchNo;
    private String productName;
    private String reasonText;
    private String riskLevel;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
