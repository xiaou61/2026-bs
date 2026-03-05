package com.alumni.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("alumni_info")
public class AlumniInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String studentNo;
    private Long gradeId;
    private Long classId;
    private Integer gender;
    private LocalDate birthday;
    private String nativePlace;
    private String political;
    private String degree;
    private String major;
    private LocalDate graduationDate;
    private String company;
    private String position;
    private String industry;
    private String city;
    private String introduction;
    private Integer isContact;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String userAvatar;
    @TableField(exist = false)
    private String userPhone;
    @TableField(exist = false)
    private String userEmail;
    @TableField(exist = false)
    private String gradeName;
    @TableField(exist = false)
    private String className;
}
