package com.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("standing_record")
public class StandingRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long seasonId;
    private Long teamId;
    private Integer playedCount;
    private Integer winCount;
    private Integer drawCount;
    private Integer lossCount;
    private Integer goalFor;
    private Integer goalAgainst;
    private Integer goalDiff;
    private Integer points;
    private Integer ranking;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String seasonName;
    @TableField(exist = false)
    private String teamName;
}
