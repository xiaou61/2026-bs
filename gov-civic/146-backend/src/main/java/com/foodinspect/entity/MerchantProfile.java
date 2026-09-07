package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MerchantProfile {
    private Long id;
    private String merchantNo;
    private String merchantName;
    private String contactName;
    private String contactPhone;
    private String businessAddress;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






