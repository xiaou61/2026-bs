package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("export_approval")
public class ExportApproval {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String approvalNo;
    private String datasetName;
    private String exportType;
    private String applicantName;
    private String fileLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
