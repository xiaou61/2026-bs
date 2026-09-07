package com.esgreport.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DisclosureTemplate {
    private Long id;
    private String templateNo;
    private String templateName;
    private String industryName;
    private String versionName;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
