package com.enrollment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("student")
public class Student {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String examNo;
    private String name;
    private Integer gender;
    private String idCard;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String province;
    private String city;
    private String address;
    private String highSchool;
    private String photo;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
