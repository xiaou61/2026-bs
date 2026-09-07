package com.security.vo;

import lombok.Data;

@Data
public class UserVO {
    private Long id;
    private String nickname;
    private String avatar;
    private Integer points;
    private Integer level;
    private String levelName;
}
