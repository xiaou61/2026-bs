package com.droneinspect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("defect_report")
public class DefectReport {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String defectNo;
    private String zoneName;
    private String defectType;
    private String severityLevel;
    private String reporterName;
    private Integer photoCount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
