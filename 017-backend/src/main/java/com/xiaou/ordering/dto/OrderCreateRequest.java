package com.xiaou.ordering.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderCreateRequest {
    private Long merchantId;
    private Integer pickupType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pickupTime;
    private String userRemark;
}
