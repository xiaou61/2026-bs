package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class HomeProfile {
    private Long id;
    private String homeNo;
    private String ownerName;
    private String cityName;
    private String communityName;
    private BigDecimal areaSize;
    private Integer memberCount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
