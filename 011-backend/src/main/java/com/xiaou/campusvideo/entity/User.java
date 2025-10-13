package com.xiaou.campusvideo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String username;
    
    @TableField(select = false)
    private String password;
    
    private String nickname;
    
    private String avatar;
    
    private Integer gender;
    
    private String phone;
    
    private String studentId;
    
    private String school;
    
    private String college;
    
    private String major;
    
    private String signature;
    
    private Integer level;
    
    private Integer points;
    
    private Integer likeCount;
    
    private Integer fansCount;
    
    private Integer followCount;
    
    private Integer videoCount;
    
    private String role;
    
    private Integer status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    private Boolean isFollow;
}

