package com.xiaou.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

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
