package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CarrierCompany {
    private Long id;
    private String companyName;
    private String companyCode;
    private String licenseNo;
    private String contactName;
    private String contactPhone;
    private Integer vehicleCount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
