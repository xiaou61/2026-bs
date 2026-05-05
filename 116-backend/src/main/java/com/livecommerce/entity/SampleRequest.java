package com.livecommerce.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SampleRequest {
    private Long id;
    private String requestNo;
    private String productName;
    private String supplierName;
    private String receiverName;
    private String expressNo;
    private String requestDate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
