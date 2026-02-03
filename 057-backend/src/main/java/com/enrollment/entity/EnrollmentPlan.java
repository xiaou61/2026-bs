package com.enrollment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("enrollment_plan")
public class EnrollmentPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer year;
    private Long majorId;
    private Integer planCount;
    private Integer actualCount;
    private Integer minScore;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String majorName;
    @TableField(exist = false)
    private String departmentName;
}
