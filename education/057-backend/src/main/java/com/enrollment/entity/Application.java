package com.enrollment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("application")
public class Application {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long planId;
    private Long firstMajorId;
    private Long secondMajorId;
    private Integer adjust;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String studentName;
    @TableField(exist = false)
    private String examNo;
    @TableField(exist = false)
    private String firstMajorName;
    @TableField(exist = false)
    private String secondMajorName;
    @TableField(exist = false)
    private Integer planYear;
}
