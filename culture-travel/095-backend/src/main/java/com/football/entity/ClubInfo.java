package com.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("club_info")
public class ClubInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String clubNo;
    private String clubName;
    private String shortName;
    private String city;
    private Integer foundedYear;
    private String chairman;
    private String contactPhone;
    private Integer status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
