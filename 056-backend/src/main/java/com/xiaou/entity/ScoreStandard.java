package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("score_standard")
public class ScoreStandard {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long competitionId;
    private String name;
    private BigDecimal maxScore;
    private BigDecimal weight;
    private String description;
    private Integer sort;
    private LocalDateTime createTime;
}
