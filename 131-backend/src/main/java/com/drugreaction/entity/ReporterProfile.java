package com.drugreaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("reporter_profile")
public class ReporterProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String reporterNo;
    private String reporterName;
    private String organizationName;
    private String phoneNumber;
    private String roleType;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
