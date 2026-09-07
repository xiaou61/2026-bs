package com.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("coach_info")
public class CoachInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String coachNo;
    private Long teamId;
    private String name;
    private String nationality;
    private Integer age;
    private String formation;
    private LocalDate tenureStart;
    private String phone;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String teamName;
}
