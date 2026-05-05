package com.livecommerce.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AnchorProfile {
    private Long id;
    private String anchorName;
    private String anchorNo;
    private String specialCategory;
    private String levelName;
    private BigDecimal commissionRate;
    private String phone;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
