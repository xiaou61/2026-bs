package com.bike.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class RideOrder {
    private Long id;
    private String orderNo;
    private Long userId;
    private Long bikeId;
    private Long startStationId;
    private Long endStationId;
    private Date startTime;
    private Date endTime;
    private Integer duration;
    private BigDecimal distance;
    private BigDecimal amount;
    private Integer status;
    private Integer payStatus;
    private Date createTime;
    private String username;
    private String bikeNo;
    private String startStationName;
    private String endStationName;
}
