package com.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("player_info")
public class PlayerInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String playerNo;
    private Long teamId;
    private String name;
    private Integer jerseyNumber;
    private String position;
    private Integer age;
    private String nationality;
    private Integer goalCount;
    private Integer assistCount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String teamName;
}
