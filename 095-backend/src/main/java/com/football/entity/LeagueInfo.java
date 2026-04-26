package com.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("league_info")
public class LeagueInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String leagueNo;
    private String name;
    private String region;
    private String organizer;
    private String levelType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
