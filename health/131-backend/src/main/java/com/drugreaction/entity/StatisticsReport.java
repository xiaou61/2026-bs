package com.drugreaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("statistics_report")
public class StatisticsReport {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String statNo;
    private String deptName;
    private String statMonth;
    private Integer reportCount;
    private Integer seriousCount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
