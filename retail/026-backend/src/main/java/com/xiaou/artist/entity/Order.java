package com.xiaou.artist.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private String orderNo;
    private Long demandId;
    private Long userId;
    private Long artistId;
    private String title;
    private String description;
    private BigDecimal totalPrice;
    private BigDecimal deposit;
    private BigDecimal finalPayment;
    private String status;
    private String draftUrl;
    private String finalUrl;
    private Integer reviseCount;
    private Integer maxRevise;
    private BigDecimal commissionRate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    // 关联字段
    private String username;
    private String artistName;
}
