package com.enrollment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("admission")
public class Admission {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long planId;
    private Long majorId;
    private Integer score;
    private String admissionNo;
    private Integer status;
    private LocalDateTime admitTime;
    private LocalDateTime confirmTime;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String studentName;
    @TableField(exist = false)
    private String examNo;
    @TableField(exist = false)
    private String majorName;
    @TableField(exist = false)
    private Integer planYear;
}
