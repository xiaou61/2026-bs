package com.game.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TradeOrderVO {
    private Long id;
    private String orderNo;
    private Long userId;
    private Long itemId;
    private Long sellerId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Integer status;
    private String remark;
    private LocalDateTime payTime;
    private LocalDateTime finishTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String buyerName;
    private String sellerName;
    private String itemTitle;
}
