package com.enrollment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("score")
public class Score {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Integer year;
    private Integer chinese;
    private Integer math;
    private Integer english;
    private Integer comprehensive;
    private Integer totalScore;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String studentName;
    @TableField(exist = false)
    private String examNo;
}
