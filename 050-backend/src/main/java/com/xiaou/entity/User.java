package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String realName;
    private String avatar;
    private String phone;
    private String email;
    private String openid;
    private String studentNo;
    private String teacherNo;
    private String department;
    private String className;
    private Integer role; // 0-学生 1-教师 2-管理员
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
