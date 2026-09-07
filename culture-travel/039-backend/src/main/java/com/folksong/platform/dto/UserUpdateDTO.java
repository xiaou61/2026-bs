package com.folksong.platform.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String nickname;
    private String avatar;
    private String email;
    private String phone;
    private Integer gender;
    private String introduction;
}
