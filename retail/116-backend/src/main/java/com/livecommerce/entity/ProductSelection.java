package com.livecommerce.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductSelection {
    private Long id;
    private String selectionNo;
    private String productName;
    private String brandName;
    private String categoryName;
    private BigDecimal supplyPrice;
    private BigDecimal commissionRate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
