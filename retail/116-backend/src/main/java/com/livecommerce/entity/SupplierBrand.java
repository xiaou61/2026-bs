package com.livecommerce.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SupplierBrand {
    private Long id;
    private String supplierName;
    private String brandName;
    private String contactName;
    private String contactPhone;
    private String qualificationLevel;
    private String cooperationMode;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
