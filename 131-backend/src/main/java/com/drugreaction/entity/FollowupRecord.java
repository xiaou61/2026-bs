package com.drugreaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("followup_record")
public class FollowupRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String recordNo;
    private String planNo;
    private String followTime;
    private String patientCondition;
    private String recorderName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
