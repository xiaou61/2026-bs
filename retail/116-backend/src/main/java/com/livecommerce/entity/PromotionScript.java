package com.livecommerce.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PromotionScript {
    private Long id;
    private String scriptNo;
    private String productName;
    private String mainSellingPoint;
    private String couponText;
    private String reviewUser;
    private String versionNo;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
