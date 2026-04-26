package com.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("match_schedule")
public class MatchSchedule {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String matchNo;
    private Long seasonId;
    private Integer roundNo;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long venueId;
    private LocalDateTime kickOffTime;
    private Integer homeScore;
    private Integer awayScore;
    private String status;
    private String referee;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String seasonName;
    @TableField(exist = false)
    private String homeTeamName;
    @TableField(exist = false)
    private String awayTeamName;
    @TableField(exist = false)
    private String venueName;
}
