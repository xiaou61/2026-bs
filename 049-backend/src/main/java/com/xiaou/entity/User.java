package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String openid;
    private String username;
    @JsonIgnore
    @ToString.Exclude
    private String password;
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private Integer role; // 0-学生,1-教师,2-管理员
    private String targetSchool;
    private String targetMajor;
    private Integer examYear;
    private Integer points;
    private Integer studyDays;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
