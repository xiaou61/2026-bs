package com.xiaou.express.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderCreateRequest {
    private String expressCompany;
    private String pickupCode;
    private String itemType;
    private String itemWeight;
    private String pickupAddress;
    private String deliveryAddress;
    private String deliveryBuilding;
    private String deliveryRoom;
    private BigDecimal fee;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime expectTime;
    private String contactPhone;
    private String remark;
}

