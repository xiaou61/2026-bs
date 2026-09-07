package com.bike.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PricingRule {
    private Long id;
    private String name;
    private Integer bikeType;
    private BigDecimal basePrice;
    private Integer baseDuration;
    private BigDecimal extraPrice;
    private BigDecimal dailyCap;
    private Integer status;
    private Date createTime;
}
