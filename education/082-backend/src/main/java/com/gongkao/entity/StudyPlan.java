package com.gongkao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("study_plan")
public class StudyPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long subjectId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String dailyTarget;
    private Integer completedDays;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

