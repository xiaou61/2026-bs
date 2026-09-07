package com.adoption.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("home_visit_record")
public class HomeVisitRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long applicationId;
    private Long reviewerId;
    private LocalDate visitDate;
    private String visitResult;
    private String visitRemark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String reviewerName;
}
