package com.bike.entity;

import lombok.Data;
import java.util.Date;

@Data
public class FaultReport {
    private Long id;
    private Long userId;
    private Long bikeId;
    private Integer type;
    private String description;
    private Integer status;
    private Long handlerId;
    private String handleResult;
    private Date handleTime;
    private Date createTime;
    private String username;
    private String bikeNo;
    private String handlerName;
}
