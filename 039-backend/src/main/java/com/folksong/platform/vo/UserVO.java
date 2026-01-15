package com.folksong.platform.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserVO {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String email;
    private String phone;
    private Integer gender;
    private String introduction;
    private Integer role;
    private Integer status;
    private LocalDateTime createTime;
}
