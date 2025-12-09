package com.xiaou.hair.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 用户信息VO
 */
@Data
public class UserInfoVO {
    
    private Long id;
    
    private String phone;
    
    private String nickname;
    
    private String avatar;
    
    private Integer gender;
    
    private Integer points;
    
    private String level;
    
    private BigDecimal balance;
}
