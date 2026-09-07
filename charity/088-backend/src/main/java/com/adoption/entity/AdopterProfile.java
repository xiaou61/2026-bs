package com.adoption.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("adopter_profile")
public class AdopterProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Integer gender;
    private String idCard;
    private String maritalStatus;
    private LocalDate birthDate;
    private String province;
    private String city;
    private String address;
    private String occupation;
    private String incomeLevel;
    private String familyInfo;
    private String adoptionReason;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String realName;
    @TableField(exist = false)
    private String phone;
}
