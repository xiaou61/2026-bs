package com.xiaou.artist.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Artist {
    private Long id;
    private Long userId;
    private String realName;
    private String idCard;
    private String style;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private Integer deliveryDays;
    private String acceptTypes;
    private String status;  // PENDING/APPROVED/REJECTED
    private BigDecimal rating;
    private Integer orderCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    // 关联字段
    private String username;  // 用户名
    private String avatar;    // 头像
    private String nickname;  // 昵称
}
