package com.livecommerce.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AnchorPerformance {
    private Long id;
    private String performanceNo;
    private String anchorName;
    private String sessionTitle;
    private BigDecimal gmvAmount;
    private Integer orderCount;
    private BigDecimal conversionRate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
