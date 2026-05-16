package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("rectification_notice")
public class RectificationNotice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String noticeNo;
    private String complaintTitle;
    private String rectifyRequirement;
    private String issueTime;
    private String responsibleUnit;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






