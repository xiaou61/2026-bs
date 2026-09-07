package com.xiaou.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoVO {

    private Long id;
    private String username;
    private String studentId;
    private String realName;
    private String college;
    private String dorm;
    private String phone;
    private String avatar;
    private Integer creditScore;
    private LocalDateTime createTime;
}