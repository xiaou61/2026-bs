package com.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("team_info")
public class TeamInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String teamNo;
    private Long seasonId;
    private Long clubId;
    private Long venueId;
    private String teamName;
    private String homeColor;
    private String awayColor;
    private String goalTarget;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String seasonName;
    @TableField(exist = false)
    private String clubName;
    @TableField(exist = false)
    private String venueName;
}
