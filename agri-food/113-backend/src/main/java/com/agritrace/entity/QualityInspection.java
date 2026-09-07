package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("quality_inspection")
public class QualityInspection {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String reportNo;
    private String batchNo;
    private String productName;
    private String inspectorName;
    private String inspectionDate;
    private String resultStatus;
    private String reportUrl;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
