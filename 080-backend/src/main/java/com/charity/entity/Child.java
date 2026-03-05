package com.charity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("child")
public class Child {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String idCard;
    private String province;
    private String city;
    private String district;
    private String address;
    private String school;
    private String grade;
    private String familySituation;
    private String healthStatus;
    private String photo;
    private Integer sponsorStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
