package com.enrollment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("score_line")
public class ScoreLine {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer year;
    private Long majorId;
    private String province;
    private String category;
    private String batch;
    private Integer score;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String majorName;
}
