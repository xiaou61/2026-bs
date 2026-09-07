package com.eldercare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("elder_profile")
public class ElderProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String idCard;
    private String phone;
    private String address;
    private String bloodType;
    private String chronicDisease;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
