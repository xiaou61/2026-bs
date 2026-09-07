package com.petcafe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("resident_pet")
public class ResidentPet {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String petNo;
    private Long shopId;
    private String name;
    private String petType;
    private String breed;
    private String gender;
    private Integer age;
    private String personality;
    private String healthStatus;
    private Integer starLevel;
    private String interactionStatus;
    private String avatar;
    private String introduction;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String shopName;
}
