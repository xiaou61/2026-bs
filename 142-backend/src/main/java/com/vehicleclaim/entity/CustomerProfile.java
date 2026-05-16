package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CustomerProfile {
    private Long id;
    private String customerNo;
    private String customerName;
    private String idCardNo;
    private String phone;
    private String customerType;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
