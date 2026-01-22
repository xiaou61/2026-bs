package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("study_plan")
public class StudyPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String title;
    private Long subjectId;
    private String targetContent;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal dailyHours;
    private Integer status; // 0-进行中,1-已完成,2-已放弃
    private Integer progress;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    private String subjectName;
}
