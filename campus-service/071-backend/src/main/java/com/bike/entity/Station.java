package com.bike.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Station {
    private Long id;
    private String name;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Integer capacity;
    private Integer currentCount;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
