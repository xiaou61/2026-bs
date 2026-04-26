package com.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("season_info")
public class SeasonInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String seasonNo;
    private Long leagueId;
    private String seasonName;
    private String yearLabel;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer rounds;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String leagueName;
}
