package com.xiaou.hair.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录成功响应VO
 */
@Data
@AllArgsConstructor
public class LoginVO {
    
    private String token;
    
    private UserInfoVO userInfo;
}
