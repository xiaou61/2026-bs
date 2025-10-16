package com.xiaou.campusclub.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserInfoVO {
    private Long id;
    private String username;
    private String studentId;
    private String realName;
    private String email;
    private String avatar;
    private String major;
    private Integer grade;
    private String phone;
    private Integer points;
    private Integer level;
    private List<String> interests;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

