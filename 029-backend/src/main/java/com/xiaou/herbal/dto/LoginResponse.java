package com.xiaou.herbal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long userId;

    private String username;

    private String nickname;

    private String avatar;

    private Integer userType;

    private String token;
}
