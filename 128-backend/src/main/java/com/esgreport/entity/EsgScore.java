package com.esgreport.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EsgScore {
    private Long id;
    private String scoreNo;
    private String companyName;
    private BigDecimal environmentScore;
    private BigDecimal socialScore;
    private BigDecimal governanceScore;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
