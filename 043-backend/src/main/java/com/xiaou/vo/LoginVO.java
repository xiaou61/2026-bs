package com.xiaou.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVO {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private Integer role;
    private String token;
}
