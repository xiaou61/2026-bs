package com.bike.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Bike {
    private Long id;
    private String bikeNo;
    private Integer type;
    private Integer status;
    private Long stationId;
    private Integer batteryLevel;
    private Date createTime;
    private Date updateTime;
    private String stationName;
}
