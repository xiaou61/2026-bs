package com.xiaou.rice.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentUserResponse {
    private Long id;
    private String username;
    private String phone;
    private String nickname;
    private Integer role;
    private Integer status;
}
