package com.adoption.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("child_profile")
public class ChildProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String childNo;
    private String name;
    private Integer gender;
    private LocalDate birthDate;
    private Integer age;
    private Integer status;
    private Integer adoptionStatus;
    private String avatarUrl;
    private String summary;
    private String guardianInfo;
    private LocalDate admissionDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private ChildHealthRecord healthRecord;
}
