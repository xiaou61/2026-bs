package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("judge_assignment")
public class JudgeAssignment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long competitionId;
    private Long judgeId;
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String judgeName;
}
