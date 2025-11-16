package com.xiaou.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "学号不能为空")
    private String studentId;

    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @NotBlank(message = "学院不能为空")
    private String college;

    @NotBlank(message = "宿舍号不能为空")
    private String dorm;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
}