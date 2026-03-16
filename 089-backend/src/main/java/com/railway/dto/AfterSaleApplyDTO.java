package com.railway.dto;

import lombok.Data;

@Data
public class AfterSaleApplyDTO {
    private Long orderId;
    private String serviceType;
    private Long targetScheduleId;
    private String reason;
}
