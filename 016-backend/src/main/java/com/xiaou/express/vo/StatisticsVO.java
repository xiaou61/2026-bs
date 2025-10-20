package com.xiaou.express.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticsVO {
    private Long totalUsers;
    private Long totalOrders;
    private Long todayOrders;
    private Long weekOrders;
    private Long monthOrders;
    private BigDecimal totalAmount;
    private Long completedOrders;
    private Long cancelledOrders;
}

