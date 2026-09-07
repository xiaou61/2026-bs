package com.security.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String code;
    private String nickname;
    private String avatar;
}
